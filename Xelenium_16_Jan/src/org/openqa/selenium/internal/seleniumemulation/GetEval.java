/*
Copyright 2010 Selenium committers

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */


package org.openqa.selenium.internal.seleniumemulation;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

/**
 * An implementation of the "getEval" method from Selenium.
 */
public class GetEval extends SeleneseCommand<String> {
  private final ScriptMutator mutator;

  public GetEval(ScriptMutator mutator) {
    this.mutator = mutator;
  }

  @Override
  protected String handleSeleneseCommand(WebDriver driver, String locator, String value) {
    StringBuilder builder = new StringBuilder();

    mutator.mutate(locator, builder);

    Object result = ((JavascriptExecutor) driver).executeScript(builder.toString());
    return result == null ? "" : String.valueOf(result);
  }
}
