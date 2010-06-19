/*
 * Copyright 2008-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.more.workflow.context;
import java.util.UUID;
import org.more.submit.SubmitContext;
import org.more.util.attribute.AttBase;
import org.more.util.attribute.IAttribute;
import org.more.workflow.form.FormBean;
import org.more.workflow.form.FormMetadata;
import org.more.workflow.runtime.Runtime;
import org.more.workflow.runtime.RuntimeMetadata;
/**
 * 
 * Date : 2010-5-17
 * @author Administrator
 */
public class ApplicationContext {
    private FlashSession flashSession = new FlashSession() {
                                          private IAttribute att = new AttBase();
                                          @Override
                                          public void setFlash(String flashID, IAttribute states) {
                                              att = states;
                                          }
                                          @Override
                                          public IAttribute getFlash(String flashID) {
                                              return att;
                                          }
                                      };
    /**��ȡӲSession��ӲSession�ǽ����ݴ���ڴ����ļ���*/
    public FlashSession getHardSession() {
        return flashSession;
    };
    /**��ȡ��Session����Session�ǽ����ݴ�����ڴ档*/
    public FlashSession getSoftSession() {
        return flashSession;
    };
    public FormFactory getFormFactory() {
        return new FormFactory() {
            @Override
            public FormBean createForm(FormMetadata formMetadata) {
                try {
                    return formMetadata.getFormType().newInstance();
                } catch (Exception e) {}
                return null;
            }
            @Override
            public String generateID(FormBean formBean) {
                return UUID.randomUUID().toString();
            }
            @Override
            public FormBean getForm(String formID, FormMetadata formMetadata) {
                return null;
            }
            @Override
            public void deleteForm(String formID) {}
            @Override
            public void saveForm(String formID, FormBean formBean) {}
        };
    };
    public NodeFactory getNodeFactory() {
        return null;
    };
    public RuntimeFactory getRuntimeFactory() {
        return new RuntimeFactory() {
            @Override
            public Runtime getRuntime(RunContext runContext, RuntimeMetadata runtimeMetadata) {
                try {
                    return runtimeMetadata.getRuntimeType().newInstance();
                } catch (Exception e) {}
                return null;
            }
            @Override
            public String generateID(RunContext runContext, Runtime runtime) {
                return UUID.randomUUID().toString();
            }
        };
    };
    public SubmitContext getSubmitContext() {
        return null;
    };
    public ClassLoader getContextLoader() {
        return null;
    };
};