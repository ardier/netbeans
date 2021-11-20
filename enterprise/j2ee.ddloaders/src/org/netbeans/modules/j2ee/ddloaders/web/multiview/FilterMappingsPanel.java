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

package org.netbeans.modules.j2ee.ddloaders.web.multiview;

import org.netbeans.modules.j2ee.ddloaders.web.*;
import org.netbeans.modules.xml.multiview.ui.*;

/**
 * @author  mkuchtiak
 */
public class FilterMappingsPanel extends SectionInnerPanel {
    FilterMappingsTableModel model;

    /** Creates new form JspPGPanel */
    public FilterMappingsPanel(SectionView sectionView, DDDataObject dObj) {
        super(sectionView);
        initComponents();

        // Filter Mappings
        model = new FilterMappingsTableModel();
        FilterMappingsTablePanel panel  = new FilterMappingsTablePanel(sectionView, dObj, model);
        panel.setModel(dObj.getWebApp(),dObj.getWebApp().getFilterMapping());       
        java.awt.GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        //gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
        gridBagConstraints.weightx = 1.0;
        //gridBagConstraints.weighty = 5.0;
        add(panel, gridBagConstraints);
    }
    public javax.swing.JComponent getErrorComponent(String errorId) {
        return null;
    }

    public void setValue(javax.swing.JComponent source, Object value) {    
    }
    
    public void linkButtonPressed(Object obj, String id) {
    }
    
    void removeRow(int row) {       
        model.removeRow(row);
    }
    
    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        java.awt.GridBagConstraints gridBagConstraints;

        filler = new javax.swing.JPanel();

        setLayout(new java.awt.GridBagLayout());

        filler.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        add(filler, gridBagConstraints);

    }//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel filler;
    // End of variables declaration//GEN-END:variables
    
}
