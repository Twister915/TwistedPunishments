/******************************************************************************
 Copyright 2014 Joey Sacchini                                                 *
                                                                              *
 Licensed under the Apache License, Version 2.0 (the "License");              *
 you may not use this file except in compliance with the License.             *
 You may obtain a copy of the License at                                      *
                                                                              *
     http://www.apache.org/licenses/LICENSE-2.0                               *
                                                                              *
 Unless required by applicable law or agreed to in writing, software          *
 distributed under the License is distributed on an "AS IS" BASIS,            *
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.     *
 See the License for the specific language governing permissions and          *
 limitations under the License.                                               *
 ******************************************************************************/

package me.twister915.punishments.model.storage;

public enum DBKey {
    ID("id"),
    PUNISH_TARGET("punished"),
    DATE_PUNISHED("date_punished"),
    REASON("reason"),
    ACTIVE("active"),
    LENGTH("length"),
    PUNISHER_ID("punisher_id");
    private final String key;

    DBKey(String key) {
        this.key = key;
    }

    public String toString() {
        return key;
    }
}
