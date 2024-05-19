package org.ddd.fundamental.design.stream;

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
        return null;
    }

    @Override
    public MyStream<T> filter(Predicate<T> predicate) {
        return null;
    }

    @Override
    public MyStream<T> limit(int n) {
        return null;
    }

    @Override
    public MyStream<T> distinct() {
        return null;
    }

    @Override
    public MyStream<T> peek(ForEach<T> consumer) {
        return null;
    }

    @Override
    public void forEach(ForEach<T> consumer) {

    }

    @Override
    public <R> R reduce(R initVal, BiFunction<R, R, T> accumulator) {
        return null;
    }

    @Override
    public <R, A> R collect(Collector<T, A, R> collector) {
        return null;
    }

    @Override
    public T max(Comparator<T> comparator) {
        return null;
    }

    @Override
    public T min(Comparator<T> comparator) {
        return null;
    }

    @Override
    public int count() {
        return 0;
    }

    @Override
    public boolean anyMatch(Predicate<? super T> predicate) {
        return false;
    }

    @Override
    public boolean allMatch(Predicate<? super T> predicate) {
        return false;
    }

    /**
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
}
