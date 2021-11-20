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

package org.netbeans.modules.cordova.wizard;

import java.awt.Component;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.swing.JComponent;
import javax.swing.event.ChangeListener;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.api.project.FileOwnerQuery;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectManager;
import org.netbeans.modules.cordova.CordovaPerformer;
import org.netbeans.modules.cordova.CordovaPlatform;
import org.netbeans.modules.cordova.platforms.api.PlatformManager;
import org.netbeans.modules.cordova.project.ConfigUtils;
import static org.netbeans.modules.cordova.wizard.Bundle.*;
import org.netbeans.spi.project.ui.templates.support.Templates;
import org.openide.WizardDescriptor;
import org.openide.WizardDescriptor.Panel;
import org.openide.WizardDescriptor.ProgressInstantiatingIterator;
import org.openide.filesystems.FileLock;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.NbBundle;

/**
 * @author Martin Janicek
 * @author Jan Becicka
 */
public class CordovaSampleIterator implements ProgressInstantiatingIterator<WizardDescriptor> {

    protected transient Panel[] myPanels;
    protected transient int myIndex;
    protected transient WizardDescriptor descriptor;

    @Override
    public Set<?> instantiate() throws IOException {
        assert false;
        return null;
    }

    @Override
    public void initialize(WizardDescriptor wizard) {
        descriptor = wizard;
        myPanels = createPanels(wizard);

        String[] steps = createSteps();
        for (int i = 0; i < myPanels.length; i++) {
            Component c = myPanels[i].getComponent();
            if (c instanceof JComponent) { // assume Swing components
                JComponent jc = (JComponent) c;
                // Step #.
                jc.putClientProperty(WizardDescriptor.PROP_CONTENT_SELECTED_INDEX, Integer.valueOf(i));
                // Step name (actually the whole list for reference).
                jc.putClientProperty(WizardDescriptor.PROP_CONTENT_DATA, steps);
            }
        }
    }
    
    @NbBundle.Messages({
        "LBL_NameNLocation=Name and Location",
        "LBL_CordovaSetup=Mobile Platforms Setup"})
    protected String[] createSteps() {
        if (CordovaPlatform.getDefault().isReady()) {
            return new String[] {LBL_NameNLocation()};
        } else {
            return new String[] {LBL_CordovaSetup(), LBL_NameNLocation()};
        }
    }

    @Override
    public void uninitialize(WizardDescriptor wizard) {
        myPanels = null;
    }

       @Override
    public Panel<WizardDescriptor> current() {
        return myPanels[myIndex];
    }

    @Override
    public boolean hasNext() {
        return myIndex < myPanels.length - 1;
    }

    @Override
    public boolean hasPrevious() {
        return myIndex > 0;
    }

    @Override
    public String name() {
        return null;
    }

    @Override
    public void nextPanel() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        myIndex++;
    }

    @Override
    public void previousPanel() {
        if (!hasPrevious()) {
            throw new NoSuchElementException();
        }
        myIndex--;
    }

    @Override
    public void addChangeListener(ChangeListener l) {
    }

    @Override
    public void removeChangeListener(ChangeListener l) {
    }

    protected Panel[] createPanels(WizardDescriptor wizard) {
        if (CordovaPlatform.getDefault().isReady()) {
            return new Panel[] {new SamplePanel(descriptor)};
        } else {
            return new Panel[] {new CordovaSetupPanel(descriptor),new SamplePanel(descriptor)};
        }
    }

    @Override
    public Set<?> instantiate(ProgressHandle handle) throws IOException {
        FileObject targetFolder = Templates.getTargetFolder(descriptor);

        String targetName = Templates.getTargetName(descriptor);
        FileUtil.toFile(targetFolder).mkdirs();
        FileObject projectFolder = targetFolder.createFolder(targetName);

        FileObject template = Templates.getTemplate(descriptor);
        unZipFile(template.getInputStream(), projectFolder);
        final CordovaPlatform cordovaPlatform = CordovaPlatform.getDefault();
                
        ProjectManager.getDefault().clearNonProjectCache();

        Map<String, String> map = new HashMap<String, String>();
        map.put("CordovaMapsSample", targetName);                             // NOI18N
        ConfigUtils.replaceTokens(projectFolder, map , "nbproject/project.xml"); // NOI18N
        
        final Project project = FileOwnerQuery.getOwner(projectFolder);
        CordovaPerformer.createScript(project, "mapplugins.properties", "nbproject/plugins.properties", true);
        CordovaTemplate.CordovaExtender.setPhoneGapBrowser(project);
        CordovaPerformer.getDefault().createPlatforms(project).waitFinished();
        
        return Collections.singleton(projectFolder);
    }

    private void unZipFile(InputStream source, FileObject rootFolder) throws IOException {
        try {
            ZipInputStream str = new ZipInputStream(source);
            ZipEntry entry;
            while ((entry = str.getNextEntry()) != null) {
                if (entry.isDirectory()) {
                    FileUtil.createFolder(rootFolder, entry.getName());
                    continue;
                }
                FileObject fo = FileUtil.createData(rootFolder, entry.getName());
                FileLock lock = fo.lock();
                try {
                    OutputStream out = fo.getOutputStream(lock);
                    try {
                        FileUtil.copy(str, out);
                    } finally {
                        out.close();
                    }
                } finally {
                    lock.releaseLock();
                }
            }
        } finally {
            source.close();
        }
    }    
}
