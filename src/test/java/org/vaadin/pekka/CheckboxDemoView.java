/*
 * Copyright 2018 Pekka Hyvönen pekka@vaadin.com, Vaadin Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.vaadin.pekka;

import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.IconRenderer;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.demo.DemoView;
import com.vaadin.flow.router.Route;

import java.util.Set;
import java.util.stream.Collectors;

@Route("")
public class CheckboxDemoView extends DemoView {

    public static class Person {

        private String name;
        private int id;

        public Person(String name) {
            this.name = name;
        }

        public Person(int id, String name) {
            this.name = name;
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public int getId() {
            return id;
        }

    }

    @Override
    protected void initView() {
        addBasicFeatures();
        addItemRenderer();
        addItemLabelGenerator();
        addItemIconGenerator();
        addDisabled();
        addDisabledItems();
        addComponentAfterItems();
        addReadOnlyGroup();
        insertComponentsBetweenItems();
        prependAndInsertComponents();
    }

    private void addBasicFeatures() {
        Div message = new Div();

        // begin-source-example
        // source-example-heading: Basic Checkbox group
        CheckboxGroup<String> group = new CheckboxGroup<>();
        group.setItems("foo", "bar", "baz");
        group.addValueChangeListener(event -> message.setText(String.format(
                "Checkbox group value changed from [%s] to [%s]",
                event.getOldValue(), event.getValue())));
        // end-source-example

        group.setId("checkbox-group-with-value-change-listener");
        message.setId("checkbox-group-value");

        addCard("Basic Checkbox group", group, message);
    }

    private void addItemRenderer() {
        Div message = new Div();

        // begin-source-example
        // source-example-heading: checkbox group with renderer
        CheckboxGroup<Person> group = new CheckboxGroup<>();
        group.setItems(new Person(1, "Joe"), new Person(2, "John"),
                new Person(3, "Bill"));
        group.setRenderer(new ComponentRenderer<>(person -> new Anchor(
                "http://example.com/" + person.getId(), person.getName())));
        group.addValueChangeListener(event -> message.setText(String.format(
                "Checkbox group value changed from [%s] to [%s]",
                getNames(event.getOldValue()), getNames(event.getValue()))));
        // end-source-example

        group.setId("checkbox-group-renderer");
        message.setId("checkbox-group-renderer-value");

        addCard("Checkbox group with renderer", group, message);
    }

    private void addItemLabelGenerator() {
        Div message = new Div();

        // begin-source-example
        // source-example-heading: Checkbox group with label generator
        CheckboxGroup<Person> group = new CheckboxGroup<>();
        group.setItems(new Person("Joe"), new Person("John"),
                new Person("Bill"));
        group.setRenderer(new TextRenderer<>(Person::getName));
        group.addValueChangeListener(event -> message.setText(String.format(
                "Checkbox group value changed from '%s' to '%s'",
                getNames(event.getOldValue()), getNames(event.getValue()))));
        // end-source-example

        group.setId("checkbox-group-with-item-generator");
        message.setId("checkbox-group-gen-value");

        addCard("Checkbox group with label generator", group, message);
    }

    private void addItemIconGenerator() {
        // begin-source-example
        // source-example-heading: Checkbox group with icon generator
        CheckboxGroup<Person> group = new CheckboxGroup<>();
        group.setItems(new Person(1, "Joe"), new Person(2, "John"),
                new Person(3, "Bill"));
        group.setRenderer(new IconRenderer<>(item -> {
            Image image = new Image("https://vaadin.com/images/vaadin-logo.svg",
                    "");
            image.getStyle().set("height", "15px");
            image.getStyle().set("float", "left");
            image.getStyle().set("marginRight", "5px");
            image.getStyle().set("marginTop", "2px");
            return image;
        }, Person::getName));
        // end-source-example

        group.setId("checkbox-group-icon-generator");

        addCard("Checkbox group with icon generator", group);
    }

    private void addDisabled() {

        // begin-source-example
        // source-example-heading: Disabled Checkbox group
        CheckboxGroup<String> group = new CheckboxGroup<>();
        group.setItems("foo", "bar", "baz");
        group.setEnabled(false);
        // end-source-example

        group.setId("checkbox-group-disabled");

        addCard("Disabled Checkbox group", group);
    }

    private void addReadOnlyGroup() {
        // begin-source-example
        // source-example-heading: Read-only Checkbox group
        Div valueInfo = new Div();

        CheckboxGroup<String> group = new CheckboxGroup<>();
        group.setItems("foo", "bar", "baz");
        group.setReadOnly(true);

        NativeButton button = new NativeButton("Switch read-only state",
                event -> group.setReadOnly(!group.isReadOnly()));
        group.addValueChangeListener(
                event -> valueInfo.setText(event.getValue().stream().collect(Collectors.joining(" "))));
        // end-source-example

        group.setId("checkbox-group-read-only");
        valueInfo.setId("selected-value-info");
        button.setId("switch-read-only");

        addCard("Read-only Checkbox group", group, button, valueInfo);
    }

    private void addDisabledItems() {

        Div valueInfo = new Div();
        // begin-source-example
        // source-example-heading: Checkbox group with item enabled provider
        CheckboxGroup<String> group = new CheckboxGroup<>();
        group.setItems("foo", "bar", "baz");
        group.setItemEnabledProvider(item -> !"bar".equals(item));
        // end-source-example

        group.addValueChangeListener(
                event -> valueInfo.setText(event.getValue().stream().collect(Collectors.joining(" "))));

        group.setId("checkbox-group-disabled-items");
        valueInfo.setId("checkbox-group-disabled-items-info");

        addCard("Checkbox group with item enabled provider", group, valueInfo);
    }

    private String getNames(Set<Person> persons) {
        return persons.stream().map(Person::getName).collect(Collectors.joining(", "));
    }

    private void addComponentAfterItems() {
        // begin-source-example
        // source-example-heading: Add component to group
        CheckboxGroup<String> group = new CheckboxGroup<>();
        group.setItems("foo", "bar", "baz");
        group.add(new Label("My Custom text"));

        group.getElement().getStyle().set("flexDirection", "column");
        // end-source-example

        group.setId("checkbox-group-with-appended-text");

        addCard("Add component to group", group);
    }

    private void insertComponentsBetweenItems() {
        // begin-source-example
        // source-example-heading: Insert component after item in group
        CheckboxGroup<String> group = new CheckboxGroup<>();

        // Note that setting items clear any components
        group.add(new Label("Foo group"), getFullSizeHr());

        group.setItems("foo", "bar", "baz");
        group.addComponents("foo", new Label("Not foo selections"),
                getFullSizeHr());

        group.getElement().getStyle().set("flexDirection", "column");
        // end-source-example

        group.setId("checkbox-group-with-inserted-component");

        addCard("Insert component after item in group", group);
    }

    private void prependAndInsertComponents() {
        // begin-source-example
        // source-example-heading: Insert components before item in group
        CheckboxGroup<String> group = new CheckboxGroup<>();

        group.setItems("foo", "foo-bar", "bar", "bar-foo", "baz", "baz-baz");

        group.prependComponents("foo", new Label("Foo group"), getFullSizeHr());
        group.prependComponents("bar", new Label("Bar group"), getFullSizeHr());
        group.prependComponents("baz", new Label("Baz group"), getFullSizeHr());

        group.getElement().getStyle().set("flexDirection", "column");
        // end-source-example

        group.setId("checkbox-group-with-prepended-component");

        addCard("Insert components before item in group", group);
    }

    private Hr getFullSizeHr() {
        Hr hr = new Hr();
        hr.setSizeFull();
        return hr;
    }

}