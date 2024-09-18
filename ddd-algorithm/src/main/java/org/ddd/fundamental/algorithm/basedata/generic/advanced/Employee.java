package org.ddd.fundamental.algorithm.basedata.generic.advanced;

import org.javamoney.moneta.Money;

import java.util.*;

interface Employee {
    Money getSalary();
}

/**
 * 这个接口的方法参数只能接受Employee类型
 * 而实际上的需求可能是接受 List<Manager> Manager implements Employee
 */
interface EmployeeService {
    Money calculateAvgSalary(Collection<Employee> employees);
}

interface EmployeeServicePloy {
    Money calculateAvgSalary(Collection<? extends Employee> employees);

    public static void testCalculateAvgSalary(){
        List<Manager> managers =null ;
        Set<Accountant> accountants = null ;
        Collection<SoftwareEngineer> engineers = null;
        EmployeeServicePloy employeeService = new EmployeeServicePloy() {
            @Override
            public Money calculateAvgSalary(Collection<? extends Employee> employees) {
                return null;
            }
        };
        //注意这里编译是可以通过的，而上面的接口则不行
        employeeService.calculateAvgSalary(managers);
        employeeService.calculateAvgSalary(accountants);
        employeeService.calculateAvgSalary(engineers);

        EmployeeService employeeService1 = new EmployeeService() {
            @Override
            public Money calculateAvgSalary(Collection<Employee> employees) {
                return null;
            }
        };

        //employeeService1.calculateAvgSalary(managers);
        //employeeService1.calculateAvgSalary(accountants);
        //employeeService1.calculateAvgSalary(engineers);
        //上面的代码编译错误
    }
}

class Manager implements Employee{

    @Override
    public Money getSalary() {
        return null;
    }
}

class Accountant implements Employee{

    @Override
    public Money getSalary() {
        return null;
    }
}

class SoftwareEngineer implements Employee{
    @Override
    public Money getSalary() {
        return null;
    }
}

interface SortingService {
    <T> void sort(List<T> list, Comparator<T> comparator);
}

/**
 * 为什么不这样设计接口
 * 如果接口上有泛型参数可以这样设置，但是方法有泛型参数就没啥意义啦
 * 这个例子说明了泛型参数在接口或者类上
 * 与参数在方法上是有区别的
 */
interface SortingService2{
    <T> void sort(List<? extends T> list, Comparator<? super T> comparator);
}


interface SortingService1 {
    <T> void sort(List<T> list, Comparator<? super T> comparator);

    public static void testSort(){
        Comparator<Employee> comparator = null;

        List<Manager> managers = null;
        List<Accountant> accountants = null;
        List<SoftwareEngineer> engineers = null;

        SortingService1 sortingService = new SortingService1() {
            @Override
            public <T> void sort(List<T> list, Comparator<? super T> comparator) {

            }
        };

        // All these examples compile successfullly
        sortingService.sort(managers, comparator);
        sortingService.sort(accountants, comparator);
        sortingService.sort(engineers, comparator);

        //这里如果使用另外一个接口,那么编译不会通过

        SortingService sortingService1 = new SortingService() {
            @Override
            public <T> void sort(List<T> list, Comparator<T> comparator) {

            }
        };

        //sortingService1.sort(managers, comparator);
        //sortingService1.sort(accountants, comparator);
        //sortingService1.sort(engineers, comparator);
        //编译错误

        SortingService2 sortingService2 = new SortingService2() {
            @Override
            public <T> void sort(List<? extends T> list, Comparator<? super T> comparator) {

            }
        };
        sortingService2.sort(managers, comparator);
        sortingService2.sort(accountants, comparator);
        sortingService2.sort(engineers, comparator);
    }
}

interface EmployeeRepository {
    List<? extends Employee> findEmployeesByNameLike(String nameLike);
    public static void testFindEmployeesByNameLike(){
        EmployeeRepository employeeRepository = new EmployeeRepository() {
            @Override
            public List<? extends Employee> findEmployeesByNameLike(String nameLike) {
                return null;
            }
        };

        List<? extends Employee> list = employeeRepository.findEmployeesByNameLike("");
//        list.add(new Manager());
//        list.add(new Accountant());
//        list.add(new SoftwareEngineer());
        //以上代码编译错误
        List<? extends Employee> accountants = new ArrayList<Accountant>();
        //accountants.add(new Accountant());
        //以上也是编译错误
        List<? extends Employee> managers    = new ArrayList<Manager>();
        List<? extends Employee> engineers   = new ArrayList<SoftwareEngineer>();

        //下界可以添加对象，上界不行
        // 上界能读，下界能写,因此就有了PECS
        List<? super Employee> lower = null;
        lower.add(new Manager());
        lower.add(new Accountant());
        lower.add(new SoftwareEngineer());

        List<? super Employee> employees = new ArrayList<Employee>();
        List<? super Employee> objects   = new ArrayList<Object>();
    }

}
