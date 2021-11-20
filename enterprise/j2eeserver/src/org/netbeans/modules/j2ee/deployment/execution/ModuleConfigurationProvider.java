/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.netbeans.modules.j2ee.deployment.execution;

import org.netbeans.modules.j2ee.deployment.devmodules.api.J2eeModule;
import org.netbeans.modules.j2ee.deployment.plugins.spi.config.ModuleConfiguration;


/**
 * Provide access to deployment configuration data from top-level J2EE module
 * @author  nn136682
 */
public interface ModuleConfigurationProvider {
    /**
     * @return deployment configuration data for a j2ee module.
     */
    ModuleConfiguration getModuleConfiguration();

    /**
     * Retrieve DeployableModule representing a child module
     * specifed by the provided URI.
     *
     * @param moduleUri URI for the child module to retrieve.
     * @return DeployableModule for the specified child module.
     */
    J2eeModule getJ2eeModule(String moduleUri);
}
