package chang.jlc;

@FunctionalInterface
// 这里应该泛型，最初的返回接口
public interface IConsumer {
    int copy(Object o1, Object o2);
}