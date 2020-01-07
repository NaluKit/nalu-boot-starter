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

package com.github.nalukit.bootstarternalu.server.generator.impl.common;

import com.github.nalukit.bootstarternalu.server.generator.GeneratorUtils;
import com.github.nalukit.bootstarternalu.shared.model.GeneratorException;
import com.github.nalukit.bootstarternalu.shared.model.NaluGeneraterParms;
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

public class HostPageSourceGenerator {

    private NaluGeneraterParms naluGeneraterParms;

    private File directory;

    private HostPageSourceGenerator(Builder builder) {
        super();

        this.naluGeneraterParms = builder.naluGeneraterParms;
        this.directory = builder.directory;
    }

    public static Builder builder() {
        return new Builder();
    }

    public void generate()
            throws GeneratorException {
        this.generateHostPage();
    }

    private void generateHostPage()
            throws GeneratorException {

        Configuration freeMarkerConfiguration = new Configuration();

        freeMarkerConfiguration.setClassForTemplateLoading(HostPageSourceGenerator.class,
                "/templates/html");
        freeMarkerConfiguration.setDefaultEncoding("UTF-8");

        Template template;
        try {
            template = freeMarkerConfiguration.getTemplate("HostPage.ftl");
        } catch (IOException e) {
            throw new GeneratorException("Unable to get >>HostPage.ftl<< -> exception: " + e.getMessage());
        }

        Map<String, Object> templateData = new HashMap<>();
        templateData.put("artifactId",
                GeneratorUtils.removeBadChracters(this.naluGeneraterParms.getArtefactId()));
        templateData.put("widgetLibrary",
                this.naluGeneraterParms.getWidgetLibrary()
                        .toString());

        try (StringWriter out = new StringWriter()) {
            template.process(templateData,
                    out);
            Files.write(Paths.get(this.directory.getPath() + File.separator + this.naluGeneraterParms.getArtefactId() + ".html"),
                    out.toString()
                            .getBytes());
            out.flush();
        } catch (IOException | TemplateException e) {
            throw new GeneratorException("Unable to write generated file: >>" +
                    this.directory.getPath() +
                    File.separator +
                    GeneratorUtils.setFirstCharacterToUpperCase(this.naluGeneraterParms.getArtefactId()) +
                    ".html" +
                    "<< -> exception: " +
                    e.getMessage());
        }
    }

    public static class Builder {

        NaluGeneraterParms naluGeneraterParms;

        File directory;

        public Builder naluGeneraterParms(NaluGeneraterParms naluGeneraterParms) {
            this.naluGeneraterParms = naluGeneraterParms;
            return this;
        }

        public Builder directoryWebapp(File directory) {
            this.directory = directory;
            return this;
        }

        public HostPageSourceGenerator build() {
            return new HostPageSourceGenerator(this);
        }

    }

}
