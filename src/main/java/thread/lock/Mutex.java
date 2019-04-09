package thread.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 自定义互斥锁
 */
public class Mutex implements Lock {

    /**
     * 自定义内部队列同步器
     */
    private static class Sync extends AbstractQueuedSynchronizer{

        //是否处于占有状态
        @Override
        protected boolean isHeldExclusively() {
            return getState() == 1;
        }

        //当状态为0的时候获取锁
        @Override
        protected boolean tryAcquire(int arg) {
            if (compareAndSetState(0,1)){
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        //
        @Override
        protected boolean tryRelease(int arg) {
            if (getState() == 0)
                throw new IllegalStateException();
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }

        //返回一个Condition，每个condition都包含了一个condition队列
        Condition newCondition(){return  new ConditionObject();}
    }

    //仅需要将操作代理到Sync上即可
    private final Sync sync=new Sync();

    @Override
    public void lock() {
        sync.acquire(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    public boolean isLocked(){
        //是否有独占的线程
        return sync.isHeldExclusively();
    }

    public boolean hasQueuedThreads(){
        return sync.hasQueuedThreads();
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1,unit.toNanos(time));
    }

    @Override
    public void unlock() {
        sync.release(1);
    }

    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }
}
