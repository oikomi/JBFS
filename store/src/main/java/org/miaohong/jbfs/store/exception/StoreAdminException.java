package org.miaohong.jbfs.store.exception;

/**
 * Created by miaohong on 16/1/17.
 */
public class StoreAdminException extends Exception {
//    private String msg = null;
//    public StoreAdminException(String msg) {
//        this.msg = msg;
//    }

    public static class StoreNoFreeVolumeException extends JbfsException {
        public StoreNoFreeVolumeException() {
            super(ExceptionConst.ExceptionStoreNoFreeVolume);
        }
    }

    public static class StoreVolumeExistException extends JbfsException {
        public StoreVolumeExistException() {
            super(ExceptionConst.ExceptionStoreVolumeExist);
        }
    }

    public static class NeedleTooLargeException extends JbfsException {
        public NeedleTooLargeException() {
            super(ExceptionConst.ExceptionNeedleTooLarge);
        }
    }


    public static class NeedleIsEmptyException extends JbfsException {
        public NeedleIsEmptyException() {
            super(ExceptionConst.ExceptionNeedleIsEmpty);
        }
    }

    public static class VolumeNotExistException extends JbfsException {
        public VolumeNotExistException() {
            super(ExceptionConst.ExceptionVolumeNotExist);
        }
    }

    public static class StoreFreeVolumeNotExistException extends JbfsException {
        public StoreFreeVolumeNotExistException() {
            super(ExceptionConst.ExceptionFreeVolumeNotExist);
        }
    }

//    @Override
//    public String toString() {
//        return this.msg;
//    }
}
