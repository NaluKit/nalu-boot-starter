/*
 * Copyright (c) 2018 - 2020 - Werner Elsler, Frank Hossfeld
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *  use this file except in compliance with the License. You may obtain a copy of
 *  the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *  License for the specific language governing permissions and limitations under
 *  the License.
 */

package com.github.nalukit.bootstarter.server.generator.gwt.impl.common;

import com.github.nalukit.bootstarter.shared.model.GeneratorException;
import com.github.nalukit.bootstarter.shared.model.NaluGeneraterParms;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

// TODO CSS generieren !
public class InfoGwtGenerator {
  
  private NaluGeneraterParms naluGeneraterParms;
  private String             projectFolder;
  
  private InfoGwtGenerator(Builder builder) {
    super();
    
    this.naluGeneraterParms = builder.naluGeneraterParms;
    this.projectFolder      = builder.projectFolder;
  }
  
  public static Builder builder() {
    return new Builder();
  }
  
  public void generate()
      throws GeneratorException {
    
    Configuration freeMarkerConfiguration = new Configuration();
    
    freeMarkerConfiguration.setClassForTemplateLoading(InfoGwtGenerator.class,
                                                       "/templates/gwt");
    freeMarkerConfiguration.setDefaultEncoding("UTF-8");
    
    Template template;
    try {
      template = freeMarkerConfiguration.getTemplate("readme.ftl");
    } catch (IOException e) {
      throw new GeneratorException("Unable to get >>readme.ftl<< -> exception: " + e.getMessage());
    }
    
    Map<String, Object> templateData = new HashMap<>();
    templateData.put("serverImplementation",
                     this.naluGeneraterParms.getServerImplementation()
                                            .name());
    templateData.put("artefactId",
                     this.naluGeneraterParms.getArtefactId());
    
    try (StringWriter out = new StringWriter()) {
      template.process(templateData,
                       out);
      Files.write(Paths.get(this.projectFolder + File.separator + "readme.txt"),
                  out.toString()
                     .getBytes());
      out.flush();
    } catch (IOException | TemplateException e) {
      throw new GeneratorException("Unable to write generated file: >>" + this.projectFolder + File.separator + "readme.txt" + "<< -> exception: " + e.getMessage());
    }
  }
  
  public static class Builder {
    
    NaluGeneraterParms naluGeneraterParms;
    String             projectFolder;
    
    public Builder naluGeneraterParms(NaluGeneraterParms naluGeneraterParms) {
      this.naluGeneraterParms = naluGeneraterParms;
      return this;
    }
    
    public Builder projectFolder(String projectFolder) {
      this.projectFolder = projectFolder;
      return this;
    }
    
    public InfoGwtGenerator build() {
      return new InfoGwtGenerator(this);
    }
    
  }
  
}
