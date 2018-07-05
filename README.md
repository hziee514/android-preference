# Support Types

- String
- Boolean
- boolean
- Integer
- int
- Long
- long
- Double
- double

# Usage

## Compile dependency

- gradle

```
compile 'cn.wrh.android:easy-preference:1.0.0'
```

- maven

```
<dependency>
  <groupId>cn.wrh.android</groupId>
  <artifactId>easy-preference</artifactId>
  <version>1.0.0</version>
  <type>pom</type>
</dependency>
```

## Define Model

```
class StringModel {
    private String value;
    public String getValue() {
        return this.value;
    }
    public void setValue(String value) {
        this.value = value
    }
}
```

## Define Store

```
class StringStore extends AbstractPreferenceStorage<StringModel> {
    public StringStore(Context context) {
        super(context, "string_model");
    }
}
```

## Access

```
// Save
StringModel m = new StringModel();
m.setValue("hello");
new StringStore(context).save(m);

// Load
StringModel model = new StringStore(context).load();
```

# Annotations

## Ignore

Igore field to save and load

```
@Ignore
private String field;
```

## Assign Key

Assign preference key

>priority: resId > name > fieldName

```
@Key(name = "hello", resId = R.string.hello)
private String field;
```

## Default Value

Set default value

>priority: resId > value > default

```
@Default(value = "value", resId = R.string.value)
private String field;
```

## Precision

Define precision of double value

```
@Precision(scale = 2, roundingMode = RoundingMode.FLOOR)
private double field;
```

# Proguard

```
-keepattributes *Annotation*
```

# License

```
 Copyright (C) 2018, wurenhai

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
```