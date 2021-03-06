/*******************************************************************************
 *
 *  *  * Copyright 2015 Deborah Maria Vieira Magalhães
 *  *  * 
 *  *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  *  * you may not use this file except in compliance with the License.
 *  *  * You may obtain a copy of the License at
 *  *  * 
 *  *  *     http://www.apache.org/licenses/LICENSE-2.0
 *  *  * 
 *  *  * Unless required by applicable law or agreed to in writing, software
 *  *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *  * See the License for the specific language governing permissions and
 *  *  * limitations under the License.
 *******************************************************************************/

package org.cloudbus.cloudsim;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public enum InfrastructureConfiguration {

	INSTANCE;

	private static Properties properties = System.getProperties();

	static {
		loadPropertiesFromFile("infrastructure.properties");
	}

	public String getProperty(String key) {
		return properties.getProperty(key);
	}

	public void setProperty(String key, String value) {
		properties.setProperty(key, value);
	}

	private static void loadPropertiesFromFile(String file) {
		File propertiesFile = new File(file);
		try {
			properties.load(new FileInputStream(propertiesFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
