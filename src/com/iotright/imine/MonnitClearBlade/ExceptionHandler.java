package com.iotright.imine.MonnitClearBlade;

import com.monnit.mine.MonnitMineAPI.iExceptionHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExceptionHandler implements iExceptionHandler {

    private static final Logger logger = LogManager.getLogger(ExceptionHandler.class.getName());

    @Override
    public void LogException(Exception e) {
        logger.error("CAUGHT EXCEPTION: \n" + e.getMessage());
    }

}
