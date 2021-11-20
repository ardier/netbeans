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
package org.netbeans.modules.j2ee.sun.ddloaders.multiview.common;

import org.netbeans.modules.j2ee.sun.dd.api.CommonDDBean;

/**
 *
 * @author Peter Williams
 */
public interface BeanResolver {

    // for sun beans

    public CommonDDBean createBean();

    public String getBeanName(CommonDDBean sunBean);
    
    public void setBeanName(CommonDDBean sunBean, String name);
    
    public String getSunBeanNameProperty();
    
    // for standard beans
    
    public String getBeanName(org.netbeans.modules.j2ee.dd.api.common.CommonDDBean standardBean);
    
    public String getStandardBeanNameProperty();
    
}
