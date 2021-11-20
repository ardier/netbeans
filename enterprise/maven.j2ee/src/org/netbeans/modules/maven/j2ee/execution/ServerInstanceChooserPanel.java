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

package org.netbeans.modules.maven.j2ee.execution;

import org.netbeans.modules.j2ee.deployment.devmodules.api.Deployment;
import org.netbeans.modules.j2ee.deployment.devmodules.api.InstanceRemovedException;

/**
 * Panel for choosing one of the given server instances.
 * <p>
 *
 * @author Martin Janicek
 */
public class ServerInstanceChooserPanel extends javax.swing.JPanel {

    public ServerInstanceChooserPanel(String[] serverInstances) {
        initComponents();

        for (String serverInstanceID : serverInstances) {
            try {
                jCBServer.addItem(new ServerInstanceItem(serverInstanceID));
            } catch (InstanceRemovedException ex) {
                // Instance was removed in the meantime --> just skip it and continue
            }
        }
    }

    public String getChosenServerInstance() {
        return ((ServerInstanceItem) jCBServer.getSelectedItem()).instanceID;
    }

    private static class ServerInstanceItem implements Comparable<ServerInstanceItem> {

        private final String instanceID;
        private final String displayName;

        public ServerInstanceItem(String instanceID) throws InstanceRemovedException {
            this.instanceID = instanceID;
            this.displayName = Deployment.getDefault().getServerInstance(instanceID).getDisplayName();
        }

        @Override
        public String toString() {
            return displayName;
        }

        @Override
        public int compareTo(ServerInstanceItem o) {
            return this.displayName.compareTo(o.displayName);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jCBServer = new javax.swing.JComboBox();
        jLabelTitle = new javax.swing.JLabel();

        org.openide.awt.Mnemonics.setLocalizedText(jLabelTitle, org.openide.util.NbBundle.getMessage(ServerInstanceChooserPanel.class, "ServerInstanceChooserPanel.jLabelTitle.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                    .addComponent(jCBServer, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCBServer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox jCBServer;
    private javax.swing.JLabel jLabelTitle;
    // End of variables declaration//GEN-END:variables
}
