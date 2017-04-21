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
import utils.DataUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.util.*;

/**
 * Created by Juan on 25/02/2015.
 */
@Path("/v1/files")
@Api(value = "/v1/files", description = "End point for customer related")
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
    public Response uploadFile(@FormDataParam("file") InputStream uploadedInputStream, @FormDataParam("file") FormDataContentDisposition fileDetail) throws IOException
    {
        String uploadedFileLocation = fileDetail.getFileName();
        LOGGER.info(uploadedFileLocation);
        // save it
        writeToFile(uploadedInputStream, uploadedFileLocation);
        String output = uploadedFileLocation;

        return Response.ok(output).build();
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

    @POST
    @Path("/parse")
    @ApiOperation(value="Return ApiResponseData", response = Response.class, notes="some day this will do more, it believes in a growth mentality.")
    @ApiResponses(value={
            @ApiResponse(code=400, message="Invalid ID")
    })
    public Response readExcelFile(@FormParam("file_name") String fileName, @HeaderParam("X-DreamFactory-Session-Token") String sessionToken)
    {
        try {

            FileInputStream excelFile = new FileInputStream(new File(fileName));
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet datatypeSheet = workbook.getSheetAt(0);

            List<OrderPojo> orderPojoList = new ArrayList<OrderPojo>();

            int i = 0;
            for (Row currentRow : datatypeSheet)
            {
                if(i > 0)
                {
                    int j = 0;
                    OrderPojo orderPojo = new OrderPojo();
                    for (Cell currentCell : currentRow)
                    {

                        if( j ==0)
                        {
                            orderPojo.setSourceId(DataUtils.getInt(currentCell.getStringCellValue()));
                        }
                        else if(j == 1)
                        {
                            orderPojo.setDestinationId(DataUtils.getInt(currentCell.getStringCellValue()));
                        }
                        else if(j == 2)
                        {
                            orderPojo.setTruckTypeId(DataUtils.getInt(currentCell.getStringCellValue()));
                        }
                        else if(j == 3)
                        {
                            orderPojo.setLoadingPointId(DataUtils.getInt(currentCell.getStringCellValue()));
                        }
                        else if(j == 4)
                        {
                            orderPojo.setUnloadingPointId(DataUtils.getInt(currentCell.getStringCellValue()));
                        }
                        else if(j == 5)
                        {
                            orderPojo.setRequiredBy((currentCell.getStringCellValue()));
                        }
                        else if(j == 6)
                        {
                            orderPojo.setTransporterId(DataUtils.getInt(currentCell.getStringCellValue()));
                        }
                        else if(j == 7)
                        {
                            orderPojo.setDriver((currentCell.getStringCellValue()));
                        }
                        else if(j == 8)
                        {
                            orderPojo.setTruckNo((currentCell.getStringCellValue()));
                        }
                        else if(j == 9)
                        {
                            orderPojo.setPrice(DataUtils.getInt(currentCell.getStringCellValue()));
                        }
                        else if(j == 10)
                        {
                            orderPojo.setLoadingCharge(DataUtils.getInt(currentCell.getStringCellValue()));
                        }
                        else if(j == 11)
                        {
                            orderPojo.setUnloadingCharge(DataUtils.getInt(currentCell.getStringCellValue()));
                        }
                        //getCellTypeEnum shown as deprecated for version 3.15
                        //getCellTypeEnum ill be renamed to getCellType starting from version 4.0
                        if (currentCell.getCellTypeEnum() == CellType.STRING) {
                            System.out.print(currentCell.getStringCellValue() + "--");
                        } else if (currentCell.getCellTypeEnum() == CellType.STRING) {
                            System.out.print(currentCell.getNumericCellValue() + "--");
                        }

                        j++;
                    }

                    orderPojoList.add(orderPojo);
                }
                System.out.println();
                i++;

                sendToApi(sessionToken, orderPojoList);
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
            String url = "";

            Map<String,String> mParams = new HashMap<String, String>();
            mParams.put("X-Dreamfactory-Session", sessionToken);

            ApiResult apiResult = ApiFetcher.makeStringRequest(url, ApiRequestType.POST, orderPojo.toString(), mParams);

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
