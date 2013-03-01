/*
 * Copyright 2013 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jboss.forge.container.modules;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.jboss.forge.container.AddonId;
import org.jboss.modules.ModuleIdentifier;

/**
 * Stores the current unique ID for a given {@link AddonId}. This Id is refreshed when hotswap operations occur.
 * 
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 */
class AddonModuleIdentifierCache
{
   private Map<AddonId, ModuleIdentifier> map = new ConcurrentHashMap<AddonId, ModuleIdentifier>();

   public void clear(AddonId addonId)
   {
      synchronized (map)
      {
         map.remove(addonId);
      }
   }

   public ModuleIdentifier getModuleId(AddonId addonId)
   {
      synchronized (map)
      {
         if (!map.containsKey(addonId))
            map.put(addonId, ModuleIdentifier.fromString(toModuleId(addonId) + "_" + UUID.randomUUID().toString()));
         return map.get(addonId);
      }
   }

   private String toModuleId(AddonId addonId)
   {
      synchronized (map)
      {
         return addonId.getName().replaceAll(":", ".") + ":" + addonId.getVersion();
      }
   }

}
