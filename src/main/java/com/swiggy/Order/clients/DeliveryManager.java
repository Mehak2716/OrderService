package com.swiggy.Order.clients;

import com.swiggy.Order.entities.Location;
import com.swiggy.Order.exceptions.GrpcServiceUnavailableException;
import com.swiggy.Order.exceptions.GrpcUnknownException;
import com.swiggy.Order.exceptions.NoAvailableDeliveryPartnerException;
import fulfillment.DeliveryRequest;
import fulfillment.DeliveryServiceGrpc;
import fulfillment.Response;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.springframework.stereotype.Component;

import static com.swiggy.Order.constants.literals.ResponseMessage.*;

@Component
public class DeliveryManager {

    private  final static int grpcServerPort = 9000;
    public static void initiateDelivery(Long orderId, Location pickUp, Location delivery){

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", grpcServerPort).usePlaintext().build();
        DeliveryServiceGrpc.DeliveryServiceBlockingStub client = DeliveryServiceGrpc.newBlockingStub(channel);
        try {
            Response response = client.initiateDelivery(
                    DeliveryRequest.newBuilder().setOrderId(orderId).setDeliveryLocation(
                          fulfillment.Location.newBuilder().
                                  setXCordinate((float) delivery.getXcordinate()).
                                  setYCordinate((float) delivery.getYcordinate()).
                                  build()
                    ).setPickUpLocation(
                            fulfillment.Location.newBuilder().
                                    setXCordinate((float) pickUp.getXcordinate()).
                                    setYCordinate((float) pickUp.getYcordinate()).
                                    build()
                    ).build());

        } catch (StatusRuntimeException s) {
            if (s.getStatus().getCode() == Status.Code.NOT_FOUND) {
                throw new NoAvailableDeliveryPartnerException(DELIVERY_PARTNER_UNAVAILABLE);
            }
            if (s.getStatus().getCode() == Status.Code.UNAVAILABLE) {
                throw new GrpcServiceUnavailableException(SERVICE_UNAVAILABLE);
            }
            throw new GrpcUnknownException(GRPC_UNKNOWN_ERROR);
        }
    }
}
