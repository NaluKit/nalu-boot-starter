/*
 * Copyright (c) 2018 - Frank Hossfeld
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *  use this file except in compliance with the License. You may obtain a copy of
 *  the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *  License for the specific language governing permissions and limitations under
 *  the License.
 *
 */

package com.github.nalukit.gwtbootstarternalu.client.ui.content.splitter.project;

import com.github.nalukit.gwtbootstarternalu.client.ApplicationContext;
import com.github.nalukit.nalu.client.component.AbstractCompositeController;
import com.github.nalukit.nalu.client.component.annotation.CompositeController;
import elemental2.dom.HTMLElement;

@CompositeController(componentInterface = IProjectComponent.class,
                     component = ProjectComponent.class)
public class ProjectSplitter
    extends AbstractCompositeController<ApplicationContext, IProjectComponent, HTMLElement>
  implements IProjectComponent.Controller {

  public ProjectSplitter() {
  }

  public void edit() {
    this.component.edit(this.context.getNaluGeneraterParms());
  }

  public void flush() {
    this.context.setNaluGeneraterParms(this.component.flush(this.context.getNaluGeneraterParms()));
  }
}
