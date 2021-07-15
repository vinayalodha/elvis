# Elvis Operator for Java (sort of

)

One way of writing null safe code in Java is using `Optional.ofNullable`, Even though I feel it is better alternative to
writing `if` it gets repetitive and verbose.

I have written `Optional.ofNullable` countless number of times, and I don't want to write it anymore :)

This project aims to mitigate those problems using Java Compiler Plugin API.

## Usage

Consider you need to fetch `cardNumber` from `order` object, given order and its nested objects can be null, code
snippet would look something like below

### Java 7

    public String getCardNumberFromOrder(Order order) {
        if(order != null 
                && order.getPayment() != null
                && order.getPayment().getCard() != null) {
            return order.getPayment().getCard().getCardNumber();
        }
        return null;
    }

### Java 8

Functional way with Java 8

    public String getCardNumberFromOrder(Order order) {
        return Optional.ofNullable(order)
            .map(Order::getPayment)
            .map(Payment::getCard)
            .map(Card::getCardNumber)
            .orElse(null);
    }

### Elvis Plugin

    public String getCardNumberFromOrder(Order order) {
        @NullSafe
        String cardNumber = order.getPayment().getCard().getCardNumber();
        return cardNumber;
    }

If you open class file for this code it will look something like below

    public String getCardNumberFromOrder(Order order) {
        return Optional.ofNullable(order)
            .map(Order::getPayment)
            .map(Payment::getCard)
            .map(Card::getCardNumber)
            .orElse(null);
    }

This is done using AST manipulation during compile time.

If you want to assign default value to cardNumber in above example, then code will look something like below

    public String getCardNumberFromOrder(Order order) {
        @NullSafe("1111222233334444")
        String cardNumber = order.getPayment().getCard().getCardNumber();
        return cardNumber;
    }

For more examples please refer [here](./plugin-test/src/test/java/elvis/plugin/positive/AstTransformationTests.java)
and [here](./plugin-test/src/test/java/elvis/plugin/positive)
.

## Installation

### Maven configuration

You need to add `-Xplugin:ElvisPlugin` in compiler arguments

    <dependencies>
        <dependency>
            <groupId>io.github.vinayalodha</groupId>
            <artifactId>elvis-annotation</artifactId>
            <version>0.0.1-SNAPSHOT</version>
        </dependency>
        <dependency>
            <groupId>io.github.vinayalodha</groupId>
            <artifactId>elvis-plugin</artifactId>
            <version>0.0.1-SNAPSHOT</version>
            <scope>provided</scope>
        </dependency>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>
    <plugin>
        <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <version>3.8.1</version>
            <configuration>
                <source>11</source>
                <target>11</target>
                <compilerArgs>
                    <arg>-Xplugin:ElvisPlugin</arg>
                </compilerArgs>
        </configuration>
    </plugin>

### IntelliJ Idea

You need to add `-Xplugin:ElvisPlugin` in compiler arguments

![Intellij Setting](./docs/Intellj%20Idea%20setting.png)

## Current limitations

### Works only on local variable

Currently, @NotNull can only be used on local variable, this is due to fact Java annotation limit target type for
annotation to local variable

for example

    public String getCardNumberFromOrder(Order order) {
        @NullSafe
        String cardNumber = order.getPayment().getCard().getCardNumber();
        return cardNumber;
    }

can not be written as

    public String getCardNumberFromOrder(Order order) {
        @NullSafe
        return order.getPayment().getCard().getCardNumber();
    }

### If you have classname in expression then you will get compilation error

For example, below code will give compilation error, given it contain classname(StringUtils) in expression

    public String getCardNumberFromOrder(Order order) {
        @NullSafe
        String cardNumber = StringUtils.INSTANCE.trim();
        return cardNumber;
    }

Above code will throw compilation error saying `Unable to find Symbol StringUtils`

Feel free to give it a try and share your thoughts!

