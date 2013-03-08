/*
 * Copyright 2013 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.jboss.forge.bootstrap.listener;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Logger;

import org.jboss.forge.container.Forge;
import org.jboss.forge.container.exception.ContainerException;
import org.jboss.forge.container.spi.ContainerLifecycleListener;

public class GreetingListener implements ContainerLifecycleListener
{
   private Logger logger = Logger.getLogger(getClass().getName());

   @Override
   public void beforeStart(Forge forge) throws ContainerException
   {
      StringWriter sw = new StringWriter();
      PrintWriter out = new PrintWriter(sw, true);
      out.println();
      out.println("    _____                    ");
      out.println("   |  ___|__  _ __ __ _  ___ ");
      out.println("   | |_ / _ \\| `__/ _` |/ _ \\  \\\\");
      out.println("   |  _| (_) | | | (_| |  __/  //");
      out.println("   |_|  \\___/|_|  \\__, |\\___| ");
      out.println("                   |___/      ");
      out.println("");
      out.print("JBoss Forge, version [ ");
      out.print(forge.getVersion());
      out.print(" ] - JBoss, by Red Hat, Inc. [ http://forge.jboss.org ]");
      out.println();
      logger.info(sw.toString());
   }

   @Override
   public void beforeStop(Forge forge) throws ContainerException
   {
      // Do nothing
   }

   @Override
   public void afterStop(Forge forge) throws ContainerException
   {
      // Do nothing
   }

}