package resources;

import apiRequest.ApiFetcher;
import apiRequest.ApiRequestType;
import apiRequest.ApiResult;
import com.sun.org.apache.xpath.internal.operations.Or;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import core.ConsoleLog;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pojos.OrderPojo;
import scala.util.parsing.combinator.testing.Str;
import utils.DataUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.util.*;

/**
 * Created by Juan on 25/02/2015.
 */
@Path("/uploadOrders")
@Api(value = "/uploadOrders", description = "End point for customer related")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.MULTIPART_FORM_DATA)
public class FileResource
{
    private static final Logger LOGGER = LoggerFactory.getLogger(FileResource.class);
    private static final String TAG = FileResource.class.getSimpleName();

    @POST
    @ApiOperation(value="Return ApiResponseData", response = Response.class, notes="some day this will do more, it believes in a growth mentality.")
    @ApiResponses(value={
            @ApiResponse(code=400, message="Invalid ID")
    })
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(@FormDataParam("file") InputStream uploadedInputStream, @FormDataParam("file") FormDataContentDisposition fileDetail, @HeaderParam("X-DreamFactory-Session-Token") String sessionToken) throws IOException
    {
        String uploadedFileLocation = fileDetail.getFileName();
        LOGGER.info(uploadedFileLocation);
        // save it
        writeToFile(uploadedInputStream, uploadedFileLocation);
        String output = uploadedFileLocation;

        Response response = readExcelFile(fileDetail.getFileName(), sessionToken);

        return response;
    }

    // save uploaded file to new location
    private void writeToFile(InputStream uploadedInputStream, String uploadedFileLocation) throws IOException
    {
        int read;
        final int BUFFER_LENGTH = 1024;
        final byte[] buffer = new byte[BUFFER_LENGTH];
        OutputStream out = new FileOutputStream(new File(uploadedFileLocation));
        while ((read = uploadedInputStream.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
        out.flush();
        out.close();

    }

    private Response readExcelFile(@FormParam("file_name") String fileName, @HeaderParam("X-DreamFactory-Session-Token") String sessionToken)
    {
        try {

            File file = new File(fileName);
            FileInputStream excelFile = new FileInputStream(file);
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet datatypeSheet = workbook.getSheetAt(0);

            List<OrderPojo> orderPojoList = new ArrayList<OrderPojo>();

            int i = 0;
            for (Row currentRow : datatypeSheet)
            {

                for (Cell currentCell : currentRow)
                {
                    if (currentCell.getCellTypeEnum() == CellType.STRING) {
                        System.out.print(currentCell.getStringCellValue() + "--");
                    } else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
                        System.out.print(currentCell.getNumericCellValue() + "--");
                    }
                }

                if(i > 0)
                {
                    OrderPojo orderPojo = new OrderPojo();
                    for(int j = 0; j<currentRow.getLastCellNum(); j++)
                    //for (Cell currentCell : currentRow)
                    {
                        Cell currentCell = currentRow.getCell(j, Row.MissingCellPolicy.RETURN_NULL_AND_BLANK);

                        if( j ==0)
                        {
                            if(currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC)
                            {
                                orderPojo.setItemId((int) currentCell.getNumericCellValue());
                            }
                        }
                        else if(j == 1)
                        {
                            if(currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC)
                            {
                                orderPojo.setTruckTypeId((int) currentCell.getNumericCellValue());
                            }
                        }
                        else if(j == 2)
                        {
                            if(currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC)
                            {
                                orderPojo.setLoadingPointId((int) currentCell.getNumericCellValue());
                            }
                        }
                        else if(j == 3)
                        {
                            if(currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC)
                            {
                                orderPojo.setUnloadingPointId((int) currentCell.getNumericCellValue());
                            }
                        }
                        else if(j == 4)
                        {
                            if(currentCell != null && currentCell.getCellTypeEnum() == CellType.STRING)
                            {
                                orderPojo.setRequiredBy((currentCell.getStringCellValue()));
                            }
                        }
                        else if(j == 5)
                        {
                            if(currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC)
                            {
                                orderPojo.setTransporterId((int) currentCell.getNumericCellValue());
                            }
                        }
                        else if(j == 6)
                        {
                            if(currentCell != null && currentCell.getCellTypeEnum() == CellType.STRING)
                            {
                                orderPojo.setDriver((currentCell.getStringCellValue()));
                            }
                        }
                        else if(j == 7)
                        {
                            if(currentCell != null && currentCell.getCellTypeEnum() == CellType.STRING)
                            {
                                orderPojo.setTruckNo((currentCell.getStringCellValue()));
                            }
                        }
                        else if(j == 8)
                        {
                            if(currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC)
                            {
                                orderPojo.setPrice((int) currentCell.getNumericCellValue());
                            }
                        }
                        else if(j == 9)
                        {
                            if(currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC)
                            {
                                orderPojo.setLoadingCharge((int) currentCell.getNumericCellValue());
                            }
                        }
                        else if(j == 10)
                        {
                            if(currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC)
                            {
                                orderPojo.setUnloadingCharge((int) currentCell.getNumericCellValue());
                            }
                        }
                        //getCellTypeEnum shown as deprecated for version 3.15
                        //getCellTypeEnum ill be renamed to getCellType starting from version 4.0
                        if (currentCell != null && currentCell.getCellTypeEnum() == CellType.STRING) {
                            System.out.print(currentCell.getStringCellValue() + "--");
                        } else if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC) {
                            System.out.print(currentCell.getNumericCellValue() + "--");
                        } else
                        {
                            System.out.print(null + "--");
                        }

                        j++;
                    }

                    orderPojoList.add(orderPojo);
                }
                System.out.println();
                i++;

                sendToApi(sessionToken, orderPojoList);
            }

            if(file.exists())
            {
                file.delete();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Response.ok().build();
    }

    private void sendToApi(String sessionToken, List<OrderPojo> orderPojoList)
    {
        for(OrderPojo orderPojo : orderPojoList)
        {
            String url = "http://graphql.fr8desk.com/graphql/";

            sessionToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjIyNDUsInVzZXJfaWQiOjIyNDUsImVtYWlsIjoic3VyZXNoaHVzdGxlQGdtYWlsLmNvbSIsImZvcmV2ZXIiOmZhbHNlLCJpc3MiOiJodHRwczpcL1wvaW50ZXJuYWwuZnI4ZGVzay5jb21cL2FwaVwvdjJcL3VzZXJcL3Nlc3Npb24iLCJpYXQiOjE0OTMwMTc3MDYsImV4cCI6MTQ5MzA1MzcwNiwibmJmIjoxNDkzMDE3NzA2LCJqdGkiOiI4YmVkMzY0OWQ4NzlmMjEyODNjM2E0ZDc3MWRjODI4MSJ9.GpNM7imansZuKPPaybDnpjYB9dw71ajQhFhUqQ0uYaU";

            Map<String,String> mParams = new HashMap<String, String>();
            mParams.put("Content-Type", "application/json");
            mParams.put("X-DreamFactory-Session-Token", sessionToken);
            mParams.put("x-dreamfactory-api-key", "e1c1734b3b0615595b18ba0b11d848ce250f77552cece647032b25f3874e3f08");

            if(orderPojo.getTransporterId() != null && orderPojo.getTruckNo() != null)
            {
                orderPojo.setStatusTypeId(4);
            }
            else if(orderPojo.getTransporterId() != null)
            {
                orderPojo.setStatusTypeId(3);
            }
            else
            {
                orderPojo.setStatusTypeId(2);
            }

            //String dataToSend = "{\"query\":\"mutation {\n  createOrder(order: {itemId: "+orderPojo.getItemId()+", statusTypeId: "+orderPojo.getStatusTypeId()+", requiredBy: \""+DataUtils.getString(orderPojo.getRequiredBy())+"\", partnerId:"+orderPojo.getTransporterId()+", driver:\""+DataUtils.getString(orderPojo.getDriver())+"\",truck:\""+DataUtils.getString(orderPojo.getTruckNo())+"\", loadingLocationId: "+orderPojo.getLoadingPointId()+", unloadingLocationId: "+orderPojo.getUnloadingPointId()+", orderPrice: {unitPrice: "+orderPojo.getPrice()+", quantity: 1, loading: "+orderPojo.getLoadingPointId()+", unloading: "+orderPojo.getUnloadingPointId()+"}}) {\n    orderId\n  }\n}\n\",\"variables\":null}";
            String dataToSend = "{\"query\":\"mutation {\\n  createOrder(order: {itemId: "+orderPojo.getItemId()+", statusTypeId: "+orderPojo.getStatusTypeId()+", requiredBy: \\\""+DataUtils.getString(orderPojo.getRequiredBy())+"\\\", partnerId:"+orderPojo.getTransporterId()+", driver:\\\""+orderPojo.getDriver()+"\\\",truck:\\\""+orderPojo.getTruckNo()+"\\\", loadingLocationId: "+orderPojo.getLoadingPointId()+", unloadingLocationId: "+orderPojo.getUnloadingPointId()+", orderPrice: {unitPrice: "+orderPojo.getPrice()+", quantity: 1, loading: "+orderPojo.getLoadingCharge()+", unloading: "+orderPojo.getUnloadingCharge()+"}}) {\\n    orderId\\n  }\\n}\",\"variables\":null}";

            ConsoleLog.i(TAG, dataToSend);

            ApiResult apiResult = ApiFetcher.makeStringRequest(url, ApiRequestType.POST, dataToSend, mParams);

            if(apiResult.isSuccess())
            {
                ConsoleLog.i(TAG, apiResult.getResult());
            }
            else
            {
                ConsoleLog.w(TAG, apiResult.getError());
            }
        }
    }
}
