/*
 * Copyright 2021 the original author or authors.
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

package org.gradle.smoketests

import org.gradle.internal.reflect.validation.ValidationMessageChecker

import static org.gradle.internal.reflect.validation.Severity.ERROR
import static org.gradle.internal.reflect.validation.Severity.WARNING

class NodePluginsSmokeTest extends AbstractPluginValidatingSmokeTest implements ValidationMessageChecker {
    @Override
    Map<String, Versions> getPluginsToValidate() {
        [
            // Grunt and Gulp plugins are not properly published on the plugin portal
            //'com.moowork.grunt': TestedVersions.node,
            //'com.moowork.gulp': TestedVersions.node,
            'com.moowork.node': TestedVersions.node,
        ]
    }

    @Override
    void configureValidation(String testedPluginId, String version) {
        validatePlugins {
            onPlugin('com.moowork.node') {
                failsWith([
                    "Type 'NpmSetupTask': property 'args' is not annotated with an input or output annotation.": WARNING,
                    "Type 'NpmSetupTask': setter 'setArgs()' should not be annotated with: @Internal. Method 'setArgs' is annotated with an input/output annotation and there is no corresponding getter. Possible solution: Remove the annotations. ${learnAt("validation_problems", "ignored_annotations_on_method")}.": ERROR,
                    "Type 'YarnSetupTask': property 'args' is not annotated with an input or output annotation.": WARNING
                ])
            }
        }
    }
}
