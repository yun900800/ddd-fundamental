# JPA 学习知识积累
1. 针对每个继承自Repository的接口,JPA会创建一下三个对象
    - SimpleJpaRepository —— 用来进行默认的 DAO 操作，是所有 Repository 的默认实现
    - JpaRepositoryFactoryBean —— 装配 bean，装载了动态代理 Proxy，会以对应的 DAO 的 beanName 为 key 注册到DefaultListableBeanFactory中，在需要被注入的时候从这个 bean 中取出对应的动态代理 Proxy 注入给 DAO
    - JdkDynamicAopProxy —— 动态代理对应的InvocationHandler，负责拦截 DAO 接口的所有的方法调用，然后做相应处理，比如findByUsername被调用的时候会先经过这个类的 invoke 方法

2. 疑问之一,这些接口是怎么初始化为具体的代理实例的?
    - 第一个猜想显然就是容器在初始化的时候产生的;AbstractApplicationContext.refresh方法中的
      finishBeanFactoryInitialization(beanFactory);
    - 为什么这个bean是一个JpaRepositoryFactoryBean?在这个类
      AbstractRepositoryConfigurationSourceSupport.registerBeanDefinitions 里有一个方法
      RepositoryConfigurationDelegate.registerRepositoriesIn 里面有一段
      beanDefinition.setAttribute(FACTORY_BEAN_OBJECT_TYPE, configuration.getRepositoryInterface());
      JpaRepositoryConfigExtension类的
      ```shell
         @Override
         public String getRepositoryFactoryBeanClassName() {
             return JpaRepositoryFactoryBean.class.getName();
         }
      ```
      
    - 最后看JpaRepositoryFactoryBean这个类的实现,关键看getObject方法;这个方法返回的是
      
       ```shell
         public T getObject() {
             return this.repository.get();
         }
       ```
      可以看出是从repository返回的对象;关键方法是:
       ```shell
      public void afterPropertiesSet() {
      }
       ```
      从RepositoryFactorySupport.getRepository可以看出代理出来的对象的类型都是
      SimpleJpaRepository类型
    - 剩下的就是采用动态代理来实现SimpleJpaRepository类型的代理对象,而这个类型有实现了接口
      JpaRepository和JpaSpecificationExecutor