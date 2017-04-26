/**
 * Copyright 2015-2016 Red Hat, Inc, and individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wildfly.swarm.monitor.runtime;

import javax.enterprise.context.ApplicationScoped;

import org.jboss.shrinkwrap.api.Archive;
import org.wildfly.swarm.SwarmInfo;
import org.wildfly.swarm.spi.api.ArchivePreparer;
import org.wildfly.swarm.undertow.WARArchive;

/**
 * @author Ken Finnigan
 */
@ApplicationScoped
public class InstallMonitorFilter implements ArchivePreparer {

    @Override
    public void prepareArchive(Archive<?> archive) {
        try {
            WARArchive warArchive = archive.as(WARArchive.class);
            warArchive.addDependency("org.wildfly.swarm:health-api:jar:" + SwarmInfo.VERSION);
            warArchive.findWebXmlAsset().setContextParam("resteasy.scan", "true");
        } catch (Exception e) {
            throw new RuntimeException("Failed to install health processor", e);
        }
    }
}