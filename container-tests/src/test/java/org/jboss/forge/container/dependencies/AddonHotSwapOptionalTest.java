package org.jboss.forge.container.dependencies;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.forge.arquillian.archive.ForgeArchive;
import org.jboss.forge.container.AddonDependency;
import org.jboss.forge.container.AddonId;
import org.jboss.forge.container.AddonRegistry;
import org.jboss.forge.container.AddonRepository;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 */
@RunWith(Arquillian.class)
public class AddonHotSwapOptionalTest
{
   @Deployment(order = 3)
   public static ForgeArchive getDeployment()
   {
      ForgeArchive archive = ShrinkWrap.create(ForgeArchive.class)
               .addBeansXML();

      return archive;
   }

   @Deployment(name = "dep,1", testable = false, order = 2)
   public static ForgeArchive getDeploymentDep1()
   {
      ForgeArchive archive = ShrinkWrap
               .create(ForgeArchive.class)
               .addBeansXML()
               .addAsAddonDependencies(AddonDependency.create(AddonId.from("dep", "2"), false, true));

      return archive;
   }

   @Deployment(name = "dep,2", testable = false, order = 1)
   public static ForgeArchive getDeploymentDep2()
   {
      ForgeArchive archive = ShrinkWrap
               .create(ForgeArchive.class)
               .addBeansXML();

      return archive;
   }

   @Inject
   private AddonRegistry registry;

   @Inject
   private AddonRepository repository;

   @Test
   public void testHotSwapOptional() throws Exception
   {
      AddonId depOneId = AddonId.from("dep", "1");
      AddonId depTwoId = AddonId.from("dep", "2");

      ClassLoader depTwoClassloader = registry.getRegisteredAddon(depTwoId).getClassLoader();

      repository.disable(depTwoId);
      Thread.sleep(1000);

      ClassLoader depOneClassloader = registry.getRegisteredAddon(depOneId).getClassLoader();

      repository.enable(depTwoId);
      Thread.sleep(1000);

      Assert.assertNotEquals(depOneClassloader, registry.getRegisteredAddon(depOneId).getClassLoader());
      Assert.assertNotEquals(depOneClassloader.toString(), registry.getRegisteredAddon(depOneId).getClassLoader()
               .toString());
      Assert.assertNotEquals(depTwoClassloader, registry.getRegisteredAddon(depTwoId).getClassLoader());
      Assert.assertNotEquals(depTwoClassloader.toString(), registry.getRegisteredAddon(depTwoId).getClassLoader()
               .toString());
   }
}