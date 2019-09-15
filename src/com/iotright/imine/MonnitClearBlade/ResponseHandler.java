package com.iotright.imine.MonnitClearBlade;

import com.monnit.mine.MonnitAPN.MNTEventArgs.*;
import com.monnit.mine.MonnitMineAPI.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//TODO this class is registered, but not seeing it do anything useful in response to any of the interface callbacks?
public class ResponseHandler implements MineServiceGatewayResponseAccess {

    private static final Logger logger = LogManager.getLogger(ResponseHandler.class.getName());

    @Override
    public void ProcessNetworkStatusMessage(NetworkStatusEventArgs args)
            throws Exception {

        logger.debug("Network status message: " + args.getStatus());
    }

    @Override
    public void ProcessSensorStatusIndicator(SensorStatusEventArgs args)
            throws Exception {

        logger.debug("Sensor status message: " + args.getStatus());
    }

    @Override
    public void ProcessReadDatasectorResponse(ReadConfigEventArgs args)
            throws Exception {

        logger.debug("ProcessReadDatasectorResponse message: " + args.getMsg());
    }

    @Override
    public void ProcessWriteDatasectorResponse(WriteConfigEventArgs args)
            throws Exception {

        logger.debug("ProcessWriteDataSector message: " + args.getMsg());
    }

    @Override
    public void ProcessAPPCMDResponse(ActionCtrlEventArgs args)
            throws Exception {
//        GUIListenerFunctions.print(args.getMsg());

        logger.debug("ProcessAPPCMDResponse message: " + args.getMsg());
    }

    @Override
    public void ProcessBootloaderResponse(BootloaderEventArgs args)
            throws Exception {

        logger.debug("ProcessBootloaderResponse message: " + args.getMsg());
    }

    @Override
    public void ProcessParentMsg(ParentMessageEventArgs msg) throws Exception {

        logger.debug("ProcessParentMsg message: " + msg.getMsg());
    }

    @Override
    public void ProcessDataMessage(DataMessageEventArgs msg) throws Exception {

        logger.debug("ProcessDataMessage message: " + msg.getMsg());

    }

    @Override
    public void ProccessFindGateway(FindGatewayEventArgs paramFindGatewayEventArgs) throws Exception {

        logger.debug("ProccessFindGateway for id: " + paramFindGatewayEventArgs.GatewayID);
    }

}
