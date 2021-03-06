/*
 * Copyright 2001-2005 The Apache Software Foundation.
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

package org.apache.flex.compiler.tools.patchfiles;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by christoferdutz on 16.03.16.
 */
public class ReplaceRegexpOperation extends Operation {

    private String match;
    private String replace;
    private String flags;

    public ReplaceRegexpOperation() {
    }

    public String getMatch() {
        return match;
    }

    public void setMatch(String match) {
        this.match = match;
    }

    public String getReplace() {
        return replace;
    }

    public void setReplace(String replace) {
        this.replace = replace;
    }

    public String getFlags() {
        return flags;
    }

    public void setFlags(String flags) {
        this.flags = flags;
    }

    @Override
    public void perform(File file) throws IOException {
        String content = IOUtils.toString(new FileInputStream(file), "UTF-8");
        // TODO: Add the "flags" support
        String matchExpression = match;
        if(matchExpression.contains("§{file.base}")) {
            matchExpression = matchExpression.replaceAll("§\\{file.base\\}", FilenameUtils.getBaseName(file.getName()));
        }
        String replaceExpression = (replace == null) ? "" : replace;
        if(replaceExpression.contains("§{file.base}")) {
            replaceExpression = replaceExpression.replaceAll("§\\{file.base\\}", FilenameUtils.getBaseName(file.getName()));
        }
        if(replaceExpression.contains("_")) {
            replaceExpression = replaceExpression.replaceAll("_", " ");
        }
        if(replaceExpression.contains("\\n")) {
            replaceExpression = replaceExpression.replaceAll("\\\\n", "\n");
        }

        //content = Pattern.compile(matchExpression, Pattern.DOTALL).matcher(content).replaceAll(replaceExpression);
        content = content.replaceAll(matchExpression, replaceExpression);
        IOUtils.write(content, new FileOutputStream(file), "UTF-8");
    }

}
