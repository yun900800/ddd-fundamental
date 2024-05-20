package org.ddd.fundamental.design.stream;

import java.util.HashSet;
import java.util.Set;

public class MyStream<T> implements Stream<T> {
    /**
     * 流的头部
     * */
    private T head;

    /**
     * 流的下一项求值函数
     * */
    private NextItemEvalProcess nextItemEvalProcess;

    /**
     * 是否是流的结尾
     * */
    private boolean isEnd;

    public static class Builder<T>{
        private MyStream<T> target;

        public Builder() {
            this.target = new MyStream<>();
        }

        public Builder<T> head(T head){
            target.head = head;
            return this;
        }

        Builder<T> isEnd(boolean isEnd){
            target.isEnd = isEnd;
            return this;
        }

        public Builder<T> nextItemEvalProcess(NextItemEvalProcess nextItemEvalProcess){
            target.nextItemEvalProcess = nextItemEvalProcess;
            return this;
        }

        public MyStream<T> build(){
            return target;
        }
    }

    /**
     * 当前流强制求值
     * @return 求值之后返回一个新的流
     * */
    private MyStream<T> eval(){
        return this.nextItemEvalProcess.eval();
    }

    /**
     * 当前流 为空
     * */
    private boolean isEmptyStream(){
        return this.isEnd;
    }

    @Override
    public <R> MyStream<R> map(Function<R, T> mapper) {
        NextItemEvalProcess lastNextItemEvalProcess = this.nextItemEvalProcess;
        //这个计算节点的作用是封装 Stream<T> ----> Stream<R>, 并将这个计算节点构建到原始的Stream中
        this.nextItemEvalProcess = new NextItemEvalProcess(
                ()->{
                    MyStream<T> myStream = lastNextItemEvalProcess.eval();
                    return myStream.map(mapper, myStream);
                }
        );

        // 求值链条 加入一个新的process map
        return new MyStream.Builder<R>()
                .nextItemEvalProcess(this.nextItemEvalProcess)
                .build();
    }

    @Override
    public <R> MyStream<R> flatMap(Function<? extends MyStream<R>, T> mapper) {
        NextItemEvalProcess lastNextItemEvalProcess = this.nextItemEvalProcess;
        this.nextItemEvalProcess = new NextItemEvalProcess(
                ()->{
                    MyStream<T> myStream = lastNextItemEvalProcess.eval();
                    return flatMap(mapper, Stream.makeEmptyStream(), myStream);
                }
        );

        // 求值链条 加入一个新的process map
        return new MyStream.Builder<R>()
                .nextItemEvalProcess(this.nextItemEvalProcess)
                .build();
    }

    @Override
    public MyStream<T> filter(Predicate<T> predicate) {
        NextItemEvalProcess lastNextItemEvalProcess = this.nextItemEvalProcess;
        this.nextItemEvalProcess = new NextItemEvalProcess(
                ()-> {
                    MyStream<T> myStream = lastNextItemEvalProcess.eval();
                    return filter(predicate, myStream);
                }
        );

        // 求值链条 加入一个新的process filter
        return this;
    }

    @Override
    public MyStream<T> limit(int n) {
        NextItemEvalProcess lastNextItemEvalProcess = this.nextItemEvalProcess;
        this.nextItemEvalProcess = new NextItemEvalProcess(
                ()-> {
                    MyStream<T> myStream = lastNextItemEvalProcess.eval();
                    return limit(n, myStream);
                }
        );

        // 求值链条 加入一个新的process limit
        return this;
    }

    @Override
    public MyStream<T> distinct() {
        NextItemEvalProcess lastNextItemEvalProcess = this.nextItemEvalProcess;
        this.nextItemEvalProcess = new NextItemEvalProcess(
                ()-> {
                    MyStream<T> myStream = lastNextItemEvalProcess.eval();
                    return distinct(new HashSet<>(), myStream);
                }
        );

        // 求值链条 加入一个新的process limit
        return this;
    }

    @Override
    public MyStream<T> peek(ForEach<T> consumer) {
        NextItemEvalProcess lastNextItemEvalProcess = this.nextItemEvalProcess;
        this.nextItemEvalProcess = new NextItemEvalProcess(
                ()-> {
                    MyStream<T> myStream = lastNextItemEvalProcess.eval();
                    return peek(consumer,myStream);
                }
        );

        // 求值链条 加入一个新的process peek
        return this;
    }

    @Override
    public void forEach(ForEach<T> consumer) {
        forEach(consumer,this.eval());
    }

    /**
     * 递归函数 配合API.forEach
     * */
    private static <T> void forEach(ForEach<T> consumer, MyStream<T> myStream){
        if(myStream.isEmptyStream()){
            return;
        }

        consumer.apply(myStream.head);
        forEach(consumer, myStream.eval());
    }

    @Override
    public <R> R reduce(R initVal, BiFunction<R, R, T> accumulator) {
        return reduce(initVal,accumulator,this.eval());
    }

    /**
     * 递归函数 配合API.reduce
     * */
    private static <R,T> R reduce(R initVal, BiFunction<R,R,T> accumulator, MyStream<T> myStream){
        if(myStream.isEmptyStream()){
            return initVal;
        }

        T head = myStream.head;
        R result = reduce(initVal,accumulator, myStream.eval());

        return accumulator.apply(result,head);
    }


    /**
     * 从函数式编程的角度来看，collect方法是一个高阶函数，
     * 其接受三个函数作为参数(supplier，accumulator，finisher)，最终生成一个更加强大的函数。
     *
     * @param collector 传入所需的函数组合子，生成高阶函数
     * @return
     * @param <R>
     * @param <A>
     */
    @Override
    public <R, A> R collect(Collector<T, A, R> collector) {
        // 终结操作 直接开始求值
        A result = collect(collector,this.eval());

        // 通过finish方法进行收尾
        return collector.finisher().apply(result);
    }

    /**
     * 递归函数 配合API.collect
     * */
    private static <R, A, T> A collect(Collector<T, A, R> collector, MyStream<T> myStream){
        if(myStream.isEmptyStream()){
            return collector.supplier().get();
        }

        T head = myStream.head;
        A tail = collect(collector, myStream.eval());

        return collector.accumulator().apply(tail,head);
    }

    @Override
    public T max(Comparator<T> comparator) {
        // 终结操作 直接开始求值
        MyStream<T> eval = this.eval();

        if(eval.isEmptyStream()){
            return null;
        }else{
            return max(comparator,eval,eval.head);
        }
    }

    /**
     * 递归函数 配合API.max
     * */
    private static <T> T max(Comparator<T> comparator, MyStream<T> myStream, T max){
        if(myStream.isEnd){
            return max;
        }

        T head = myStream.head;
        // head 和 max 进行比较
        if(comparator.compare(head,max) > 0){
            // head 较大 作为新的max传入
            return max(comparator, myStream.eval(),head);
        }else{
            // max 较大 不变
            return max(comparator, myStream.eval(),max);
        }
    }

    @Override
    public T min(Comparator<T> comparator) {
        // 终结操作 直接开始求值
        MyStream<T> eval = this.eval();

        if(eval.isEmptyStream()){
            return null;
        }else{
            return min(comparator,eval,eval.head);
        }
    }

    /**
     * 递归函数 配合API.min
     * */
    private static <T> T min(Comparator<T> comparator, MyStream<T> myStream, T min){
        if(myStream.isEnd){
            return min;
        }

        T head = myStream.head;
        // head 和 min 进行比较
        if(comparator.compare(head,min) < 0){
            // head 较小 作为新的min传入
            return min(comparator, myStream.eval(),head);
        }else{
            // min 较小 不变
            return min(comparator, myStream.eval(),min);
        }
    }

    @Override
    public int count() {
        return count(this.eval(),0);
    }

    /**
     * 递归函数 配合API.count
     * */
    private static <T> int count(MyStream<T> myStream, int count){
        if(myStream.isEmptyStream()){
            return count;
        }

        // count+1 进行递归
        return count(myStream.eval(),count+1);
    }

    @Override
    public boolean anyMatch(Predicate<? super T> predicate) {
        return anyMatch(predicate,this.eval());
    }


    /**
     * 递归函数 配合API.anyMatch
     * */
    private static <T> boolean anyMatch(Predicate<? super T> predicate,MyStream<T> myStream){
        if(myStream.isEmptyStream()){
            // 截止末尾，不存在任何匹配项
            return false;
        }

        // 谓词判断
        if(predicate.satisfy(myStream.head)){
            // 匹配 存在匹配项 返回true
            return true;
        }else{
            // 不匹配，继续检查，直到存在匹配项
            return anyMatch(predicate,myStream.eval());
        }
    }

    @Override
    public boolean allMatch(Predicate<? super T> predicate) {
        return allMatch(predicate,this.eval());
    }

    /**
     * 递归函数 配合API.anyMatch
     * */
    private static <T> boolean allMatch(Predicate<? super T> predicate,MyStream<T> myStream){
        if(myStream.isEmptyStream()){
            // 全部匹配
            return true;
        }

        // 谓词判断
        if(predicate.satisfy(myStream.head)){
            // 当前项匹配，继续检查
            return allMatch(predicate,myStream.eval());
        }else{
            // 存在不匹配的项，返回false
            return false;
        }
    }

    /**
     * 这个递归函数的作用是通过递归的方式将Stream<T> ---> Stream<T>
     *
     * 递归函数 配合API.map
     * */
    private static <R,T> MyStream<R> map(Function<R, T> mapper, MyStream<T> myStream){
        if(myStream.isEmptyStream()){
            return Stream.makeEmptyStream();
        }

        R head = mapper.apply(myStream.head);

        return new MyStream.Builder<R>()
                .head(head)
                .nextItemEvalProcess(new NextItemEvalProcess(()->map(mapper, myStream.eval())))
                .build();
    }

    /**
     *
     * 这里通过递归的方式将两个类合并起来
     * 递归函数 配合API.flatMap
     *
     *
     * */
    private static <R,T> MyStream<R> flatMap(Function<? extends MyStream<R>,T> mapper, MyStream<R> headMyStream, MyStream<T> myStream){
        if(headMyStream.isEmptyStream()){
            if(myStream.isEmptyStream()){
                return Stream.makeEmptyStream();
            }else{
                T outerHead = myStream.head;
                MyStream<R> newHeadMyStream = mapper.apply(outerHead);

                return flatMap(mapper, newHeadMyStream.eval(), myStream.eval());
            }
        }else{
            return new MyStream.Builder<R>()
                    .head(headMyStream.head)
                    .nextItemEvalProcess(new NextItemEvalProcess(()-> flatMap(mapper, headMyStream.eval(), myStream)))
                    .build();
        }
    }

    /**
     * 递归函数 配合API.filter
     * */
    private static <T> MyStream<T> filter(Predicate<T> predicate, MyStream<T> myStream){
        if(myStream.isEmptyStream()){
            return Stream.makeEmptyStream();
        }

        if(predicate.satisfy(myStream.head)){
            return new Builder<T>()
                    .head(myStream.head)
                    .nextItemEvalProcess(new NextItemEvalProcess(()->filter(predicate, myStream.eval())))
                    .build();
        }else{
            return filter(predicate, myStream.eval());
        }
    }

    /**
     * 递归函数 配合API.limit
     * */
    private static <T> MyStream<T> limit(int num, MyStream<T> myStream){
        if(num == 0 || myStream.isEmptyStream()){
            return Stream.makeEmptyStream();
        }

        return new MyStream.Builder<T>()
                .head(myStream.head)
                .nextItemEvalProcess(new NextItemEvalProcess(()->limit(num-1, myStream.eval())))
                .build();
    }

    /**
     * 递归函数 配合API.distinct
     * */
    private static <T> MyStream<T> distinct(Set<T> distinctSet, MyStream<T> myStream){
        if(myStream.isEmptyStream()){
            return Stream.makeEmptyStream();
        }

        if(!distinctSet.contains(myStream.head)){
            // 加入集合
            distinctSet.add(myStream.head);

            return new Builder<T>()
                    .head(myStream.head)
                    .nextItemEvalProcess(new NextItemEvalProcess(()->distinct(distinctSet, myStream.eval())))
                    .build();
        }else{
            return distinct(distinctSet, myStream.eval());
        }
    }

    /**
     * 递归函数 配合API.peek
     * */
    private static <T> MyStream<T> peek(ForEach<T> consumer,MyStream<T> myStream){
        if(myStream.isEmptyStream()){
            return Stream.makeEmptyStream();
        }

        consumer.apply(myStream.head);

        return new MyStream.Builder<T>()
                .head(myStream.head)
                .nextItemEvalProcess(new NextItemEvalProcess(()->peek(consumer, myStream.eval())))
                .build();
    }
}
