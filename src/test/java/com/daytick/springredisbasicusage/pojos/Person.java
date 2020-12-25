package com.daytick.springredisbasicusage.pojos;

/**
 * @author ly
 * @since 2020/12/23 5:03 PM
 */
public class Person {
    private String name;
    private Integer age;

    /**
     * Jackson 反序列化需要无参构造器
     *
     * @see com.fasterxml.jackson.databind.deser.BeanDeserializer#deserializeFromObject
     */
    public Person() {
    }

    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
