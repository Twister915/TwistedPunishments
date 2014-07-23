/******************************************************************************
 * Copyright 2014 Joey Sacchini                                               *
 *                                                                            *
 * Licensed under the Apache License, Version 2.0 (the "License");            *
 * you may not use this file except in compliance with the License.           *
 * You may obtain a copy of the License at                                    *
 *                                                                            *
 *     http://www.apache.org/licenses/LICENSE-2.0                             *
 *                                                                            *
 * Unless required by applicable law or agreed to in writing, software        *
 * distributed under the License is distributed on an "AS IS" BASIS,          *
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   *
 * See the License for the specific language governing permissions and        *
 * limitations under the License.                                             *
 ******************************************************************************/

package me.twister915.punishments.command;

import java.util.HashMap;
import java.util.Map;

public class TimeUtils {
    private static final Map<Character, Double> TIMEMAP = new HashMap<>();

    static {
        TIMEMAP.put('s', 1D);
        TIMEMAP.put('m', 60D);
        TIMEMAP.put('h', 3600D);
        TIMEMAP.put('d', 86400D);
        TIMEMAP.put('w', 604800D);
        TIMEMAP.put('y', 3.1536E7);
    }

    public static Double parseTime(String arg){
        //Get all the characters, example string 1d2h35m17s
        char[] chars = arg.toCharArray();
        Double seconds = 0.0; //We start with zero seconds
        for (int x = 0; x < chars.length; x++) { //Will check each char
            char characterTesting = Character.toLowerCase(chars[x]);
            if (!TIMEMAP.containsKey(characterTesting)) continue; //This will pass for each of the time markers
            Double multiplier = TIMEMAP.get(characterTesting); //Gets the multiplexer for this marker, for "d" it will be 86400
            StringBuilder number = new StringBuilder(); //Start to build all the characters before this marker
            for (int cursor = x-1; cursor >= 0; cursor--) { //Go through it from the previous character until it reaches the start of our string going in reverse
                char possibleDigit = chars[cursor]; //Gets the current character
                if (!Character.isDigit(possibleDigit)) break; //If we've reached the end of the digits, stop reading
                number.append(possibleDigit); //otherwise, append it to the string builder
            }
            String s = number.reverse().toString(); //Then, reverse it and get the string value
            Double time; //Attempt a parse
            try {
                time = Double.valueOf(s);
            } catch (NumberFormatException e) {
                continue;
            }
            seconds += time*multiplier; //And add it onto the total seconds
        }
        return seconds;
    }
}
