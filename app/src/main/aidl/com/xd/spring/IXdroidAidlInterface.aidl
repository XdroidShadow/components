// IXdroidAidlInterface.aidl
package com.xd.spring;

// Declare any non-default types here with import statements
import com.xd.spring.Person;
interface IXdroidAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
      int add(int a,int b);

      int count (in List<String> data);

       List<String> getListData();

       List<Person> getPersons();

       void  setPerson(in Person p);

}