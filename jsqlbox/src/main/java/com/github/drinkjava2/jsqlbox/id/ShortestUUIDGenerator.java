/**
* Copyright (C) 2016 Yong Zhu.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.github.drinkjava2.jsqlbox.id;

import java.security.SecureRandom;

import com.github.drinkjava2.jsqlbox.SqlBoxContext;

/**
 * Generate length 12 UUID String based on radix 36, use 0-9 a-z characters, example: mrt227os89et <br/>
 * 12 length is not strong enough but it's OK for non-critical applications.
 * 
 * @author Yong Zhu
 * @version 1.0.0
 * @since 1.0.0
 */
public class ShortestUUIDGenerator implements IdGenerator {

	private static final SecureRandom random = new SecureRandom();
	private static final char[] ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyz".toCharArray();

	@Override
	public Object getNextID(SqlBoxContext ctx) {
		return getShortestUUID();
	}

	private static String getShortestUUID() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 12; i++) {
			sb.append(ALPHABET[random.nextInt(32)]);
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		System.out.println(getShortestUUID());
	}

}