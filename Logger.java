import java.io.*;
import java.util.regex.*;
import java.util.*;
import java.text.*;

public class Logger{
    public static void main(String[] args) {
        try{
            Scanner scanner = new Scanner(System.in);
            System.out.println("ENTER THE START DATE IN (dd-MM-yyyy) FORMAT");
            String inputStartDate = scanner.next();
            System.out.println("ENTER THE END DATE IN (dd-MM-yyyy) FORMAT");
            String inputEndDate = scanner.next();

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
            simpleDateFormat.setLenient(false);
            Date pseudoDate = null;
            try{
                pseudoDate = simpleDateFormat.parse(inputStartDate);
                pseudoDate = simpleDateFormat.parse(inputEndDate);
                System.out.println("\nDate : " + inputStartDate + " to " + inputEndDate);
            }
            catch(ParseException e){
                System.out.println(e);
            }

            System.out.printf("\n%-25s%-25s", "Computer Name", "Number Of Disconnects\n");

            File folder = new File("./H--RAINTREE-PARKER94-/");
            File[] listOfFiles = folder.listFiles();
            String[] listOfFilesString = new String[listOfFiles.length];
            for (int i = 0; i < listOfFiles.length; i++)
			    listOfFilesString[i] = String.valueOf(listOfFiles[i]);
            List<String> listOfFilesList = Arrays.asList(listOfFilesString);

            String[] startDateArray = inputStartDate.split("-");
            String[] endDateArray = inputEndDate.split("-");
            String startDateString = "";
            String endDateString = "";
            for(int i=startDateArray.length-1; i>=0; i--){
                startDateString += startDateArray[i];
                endDateString += endDateArray[i];
            }

            ArrayList<String> badAss = new ArrayList<>();
            for(int i = Integer.parseInt(startDateString); i <= Integer.parseInt(endDateString); i++){
                String fooDate = "./H--RAINTREE-PARKER94-/" + String.valueOf(i) + ".log";
                if(listOfFilesList.contains(fooDate)){
                    badAss.add(fooDate);
                }
            }

            String log, me;
            String[] disconnect, name;
            int count = 0;
            HashSet<String> nameSet = new HashSet<>();;
            for(String file: badAss){
                FileInputStream fileInputStream = new FileInputStream(file);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
                while((log = bufferedReader.readLine()) != null){
                    Pattern patternName = Pattern.compile("\\s", Pattern.CASE_INSENSITIVE);
                    name = patternName.split(log);
                    if(name.length > 1){
                        patternName = Pattern.compile("[:]", Pattern.CASE_INSENSITIVE);
                        name = patternName.split(name[1]);
                        nameSet.add(name[1]);
                    }
                }

                for(String names: nameSet){
                    fileInputStream = new FileInputStream(file);
                    bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
                    while((me = bufferedReader.readLine()) != null){
                        Pattern patternName = Pattern.compile(names, Pattern.CASE_INSENSITIVE);
                        Matcher matcherName = patternName.matcher(me);
                        if(matcherName.find()){
                            Pattern patternDisconnect = Pattern.compile("[|]", Pattern.CASE_INSENSITIVE);
                            disconnect = patternDisconnect.split(me);
                            if(disconnect.length > 1){
                                Pattern patternDisconnects = Pattern.compile("Client is disconnected", Pattern.CASE_INSENSITIVE);
                                Matcher matcher = patternDisconnects.matcher(disconnect[2]);
                                if(matcher.find()){
                                    count ++;
                                }
                            }
                        }
                    }
                    System.out.printf("\n%-25s%-25s", names, String.valueOf(count));
                    count = 0;
                }
                fileInputStream.close();
                nameSet.clear();
            }
            System.out.print("\n");
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
}