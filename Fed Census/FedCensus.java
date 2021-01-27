import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.TreeMap;
import java.util.TreeSet;

public class FedCensus {
    public static void main(String[] args) {
        ArrayList<Citizen> citizens = readTextFile();
        border();
        streetMap(citizens);
        border();
        birthplaceMap(citizens);
        border();
        motherTongueMap(citizens);
        border();
        occupationMap(citizens);
        border();
        genderMap(citizens);
        border();
        rentMap(citizens);
        border();
        jobRentCorrelationMap(citizens);
    }

    public static void border() {
        System.out.println("*****************************************************************");
    }

    public static void streetMap(ArrayList<Citizen> citizens) {
        TreeMap<String, TreeSet<Citizen>> map = new TreeMap<>();
        for (Citizen citizen : citizens) {
            if (citizen.getStreet() == null || citizen.getStreet().equals("."))
                continue;
            map.putIfAbsent(citizen.getStreet(), new TreeSet<>());
            map.get(citizen.getStreet()).add(citizen);
        }

        Iterator<String> streets = map.keySet().iterator();
        while (streets.hasNext()) {
            String street = streets.next();
            System.out.println("Street: " + street);
            for (Citizen citizen : map.get(street))
                System.out.println("\t" + citizen);
        }
    }

    public static void birthplaceMap(ArrayList<Citizen> citizens) {
        TreeMap<String, PriorityQueue<Double>> map = new TreeMap<>();
        for (Citizen citizen : citizens) {
            if (citizen.getBirthplace() == null || citizen.getBirthplace().equals("."))
                continue;
            map.putIfAbsent(citizen.getBirthplace(), new PriorityQueue<>());
            map.get(citizen.getBirthplace()).add(citizen.getAge());
        }

        Iterator<String> birthplaces = map.keySet().iterator();
        while (birthplaces.hasNext()) {
            String birthplace = birthplaces.next();
            System.out.println("Birthplace: " + birthplace);
            if (birthplace.equals("Pennsylvania"))
                System.out.println("\tThere were " + map.get(birthplace).size() + " people from " + birthplace);
            else {
                System.out.print("[");
                PriorityQueue<Double> ages = map.get(birthplace);
                while (!ages.isEmpty()) {
                    double age = ages.poll();
                    if (age >= 0) {
                        if (ages.peek() != null)
                            System.out.print(age + ", ");
                        else
                            System.out.print(age);
                    }
                }
                System.out.println("]");
            }
        }
    }

    public static void motherTongueMap(ArrayList<Citizen> citizens) {
        TreeMap<String, ArrayList<String>> map = new TreeMap<>();
        for (Citizen citizen : citizens) {
            if (citizen.getMotherTongue() == null || citizen.getMotherTongue().equals("."))
                continue;
            map.putIfAbsent(citizen.getMotherTongue(), new ArrayList<>());
            map.get(citizen.getMotherTongue()).add(citizen.getLastName() + ", " + citizen.getFirstName());
        }

        Iterator<String> motherTongues = map.keySet().iterator();
        while (motherTongues.hasNext()) {
            String motherTongue = motherTongues.next();
            System.out.println(
                    "There " + ((map.get(motherTongue).size() != 1) ? "are " + map.get(motherTongue).size() + " people"
                            : "is " + map.get(motherTongue).size() + " person") + " who speak " + motherTongue);
        }
    }

    public static void occupationMap(ArrayList<Citizen> citizens) {
        TreeMap<String, HashSet<String>> map = new TreeMap<>();
        for (Citizen citizen : citizens) {
            if (citizen.getOccupation() == null || citizen.getOccupation().equals("."))
                continue;
            map.putIfAbsent(citizen.getOccupation(), new HashSet<>());
            map.get(citizen.getOccupation()).add(citizen.getfatherBirthplace());
        }
        Iterator<String> occupations = map.keySet().iterator();
        while (occupations.hasNext()) {
            String occupation = occupations.next();
            System.out.println(occupation + ":");
            HashSet<String> temp = map.get(occupation);
            Iterator<String> birthplaces = temp.iterator();
            while (birthplaces.hasNext()) {
                System.out.println("\t" + birthplaces.next());
            }
        }
    }

    public static void genderMap(ArrayList<Citizen> citizens) {
        TreeMap<String, HashSet<String>> map = new TreeMap<>();
        for (Citizen citizen : citizens) {
            if (citizen.getGender() == null)
                continue;
            map.putIfAbsent(citizen.getGender(), new HashSet<>());
            map.get(citizen.getGender()).add(citizen.getRemarks());
        }
        Iterator<String> genders = map.keySet().iterator();
        while (genders.hasNext()) {
            String gender = genders.next();
            System.out.println(gender + ": ");
            HashSet<String> temp = map.get(gender);
            Iterator<String> remarks = temp.iterator();
            while (remarks.hasNext()) {
                System.out.println("\t" + remarks.next());
            }
        }
    }

    public static void rentMap(ArrayList<Citizen> citizens) {
        TreeMap<String, TreeSet<Double>> map = new TreeMap<>();
        for (Citizen citizen : citizens) {
            if (citizen.getRenting() == null)
                continue;
            map.putIfAbsent(citizen.getRenting(), new TreeSet<>());
            map.get(citizen.getRenting()).add(citizen.getPropertyValue());
        }
        Iterator<String> rents = map.keySet().iterator();
        while (rents.hasNext()) {
            String rent = rents.next();
            System.out.println(rent + ":");
            TreeSet<Double> temp = map.get(rent);
            for (Double propertyValue : temp) {
                System.out.println("\t" + propertyValue);
            }
        }
    }

    public static void jobRentCorrelationMap(ArrayList<Citizen> citizens) {
        TreeMap<String, HashMap<String, HashSet<Double>>> map = new TreeMap<>();
        for (Citizen citizen : citizens) {
            if (citizen.getOccupation() == null || citizen.getOccupation().equals(".") || citizen.getRenting() == null
                    || citizen.getPropertyValue() == -1)
                continue;
            map.putIfAbsent(citizen.getOccupation(), new HashMap<>());
            map.get(citizen.getOccupation()).putIfAbsent(citizen.getRenting(), new HashSet<>());
            map.get(citizen.getOccupation()).get(citizen.getRenting()).add(citizen.getPropertyValue());
        }
        System.out.println("Values:");
        for (String occupation : map.keySet()) {
            System.out.println("\t" + occupation + ": " + map.get(occupation));
        }
        TreeMap<String, HashMap<String, Double>> averages = new TreeMap<>();
        for (String occupation : map.keySet()) {
            averages.put(occupation, new HashMap<>());
            for (String renting : map.get(occupation).keySet()) {
                double sum = 0;
                for (Double propertyValue : map.get(occupation).get(renting))
                    sum += propertyValue;
                averages.get(occupation).put(renting, sum / map.get(occupation).get(renting).size());
            }
        }
        System.out.println("\nAverages:");
        for (String occupation : averages.keySet()) {
            System.out.println("\t" + occupation + ": " + averages.get(occupation));
        }
    }

    public static ArrayList<Citizen> readTextFile() {
        try {
            List<String> allLines = Files.readAllLines(new File("FedCensus1930_CambriaCountyPA.txt").toPath());

            ArrayList<Citizen> citizens = new ArrayList<>();
            for (String line : allLines) {
                line = line.replaceAll("\\*/12", "    ");
                line = line.replaceAll("\\*", " ");
                if (line.length() > 3 && line.substring(0, 3).matches("\\d+")) {
                    Citizen citizen = new Citizen(line.substring(71, 88).trim(), line.substring(55, 71).trim(),
                            line.substring(20, 36).trim(), line.substring(36, 45).trim(),
                            line.substring(88, 108).trim(), line.substring(108, 113).trim(),
                            line.substring(113, 121).trim(), line.substring(133, 138).trim(),
                            line.substring(143, 151).trim(), line.substring(151, 156).trim(),
                            line.substring(156, 162).trim(), line.substring(162, 167).trim(),
                            line.substring(167, 173).trim(), line.substring(173, 190).trim(),
                            line.substring(190, 207).trim(), line.substring(207, 224).trim(),
                            line.substring(224, 235).trim(), line.substring(235, 241).trim(),
                            line.substring(252, 274).trim(), line.substring(274, 303).trim(),
                            line.substring(342).trim());
                    citizens.add(citizen);
                }
            }
            return citizens;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static double toDouble(String s) {
        s = s.replaceAll("\\$", "").replaceFirst(",", "");
        return Double.parseDouble(s);
    }

    public static double simplifyFraction(int whole, String frac) {
        String[] parts = frac.split("/");
        return whole + (double) Integer.parseInt(parts[0]) / (double) Integer.parseInt(parts[1]);
    }

    public static double splitMixedNumber(String s) {
        s = s.replaceAll("\\$", "");
        if (s.split(" ").length > 1)
            return simplifyFraction(Integer.parseInt(s.split(" ")[0]), s.split(" ")[1]);
        else
            return s.contains("/") ? simplifyFraction(0, s) : Double.parseDouble(s);
    }
}

class Citizen implements Comparable<Citizen> {

    private String firstName;
    private String lastName;
    private String street;
    private String streetNumber;
    private String relation;
    private String renting;
    private Double propertyValue;
    private String gender;
    private Double age;
    private String maritalStatus;
    private Integer ageAtFirstMarriage;
    private String attendSchool;
    private String canRead;
    private String birthplace;
    private String fatherBirthplace;
    private String motherBirthplace;
    private String motherTongue;
    private Integer yearImmigrated;
    private String occupation;
    private String industry;
    private String remarks;

    public Citizen(String firstName, String lastName, String street, String streetNumber, String relation,
            String renting, double propertyValue, String gender, double age, String maritalStatus,
            int ageAtFirstMarriage, String attendSchool, String canRead, String birthplace, String fatherBirthplace,
            String motherBirthplace, String motherTongue, int yearImmigrated, String occupation, String industry,
            String remarks) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.street = street;
        this.streetNumber = streetNumber;
        this.relation = relation;
        this.renting = renting;
        this.propertyValue = propertyValue;
        this.gender = gender;
        this.age = age;
        this.maritalStatus = maritalStatus;
        this.ageAtFirstMarriage = ageAtFirstMarriage;
        this.attendSchool = attendSchool;
        this.canRead = canRead;
        this.birthplace = birthplace;
        this.fatherBirthplace = fatherBirthplace;
        this.motherBirthplace = motherBirthplace;
        this.motherTongue = motherTongue;
        this.yearImmigrated = yearImmigrated;
        this.occupation = occupation;
        this.industry = industry;
        this.remarks = remarks;
    }

    public Citizen(String firstName, String lastName, String street, String streetNumber, String relation,
            String renting, String propertyValue, String gender, String age, String maritalStatus,
            String ageAtFirstMarriage, String attendSchool, String canRead, String birthplace, String fatherBirthplace,
            String motherBirthplace, String mothersTongue, String yearImmigrated, String occupation, String industry,
            String remarks) {
        this(firstName.equals(".") ? null : firstName, lastName.equals(".") ? null : lastName, street, streetNumber,
                relation, (renting.equals(".") || renting.equals("un") || renting.equals("")) ? null : renting,
                (propertyValue.equals(".") || propertyValue.equals("un") || propertyValue.equals("")) ? -1
                        : (propertyValue.contains(" ") ? FedCensus.splitMixedNumber(propertyValue)
                                : FedCensus.toDouble(propertyValue)),
                gender.equals(".") ? null : gender,
                (age.equals(".") || age.equals("un") || age.equals("")) ? -1 : FedCensus.splitMixedNumber(age),
                maritalStatus.equals(".") ? null : maritalStatus,
                (ageAtFirstMarriage.equals(".") || ageAtFirstMarriage.equals("un") || ageAtFirstMarriage.equals(""))
                        ? -1
                        : (ageAtFirstMarriage.equals(".") ? -1 : Integer.parseInt(ageAtFirstMarriage)),
                attendSchool, canRead, birthplace, fatherBirthplace, motherBirthplace, mothersTongue,
                (yearImmigrated.equals(".") || yearImmigrated.equals("un") || yearImmigrated.equals("")) ? -1
                        : Integer.parseInt(yearImmigrated),
                occupation, industry, remarks);
    }

    @Override
    public String toString() {
        return String.format("%-25sAge: %s", lastName + ", " + firstName, age);
    }

    @Override
    public int compareTo(Citizen o) {
        if (firstName.compareTo(o.getFirstName()) != 0)
            return firstName.compareTo(o.getFirstName());
        else if (lastName.compareTo(o.getLastName()) != 0)
            return lastName.compareTo(o.getLastName());
        else if (street.compareTo(o.getStreet()) != 0)
            return street.compareTo(o.getStreet());
        else if (streetNumber.compareTo(o.getStreetNumber()) != 0)
            return streetNumber.compareTo(o.getStreetNumber());
        else if (relation.compareTo(o.getRelation()) != 0)
            return relation.compareTo(o.getRelation());
        else if (renting.compareTo(o.getRenting()) != 0)
            return renting.compareTo(o.getRenting());
        else if (propertyValue.compareTo(o.getPropertyValue()) != 0)
            return propertyValue.compareTo(o.getPropertyValue());
        else if (gender.compareTo(o.getGender()) != 0)
            return gender.compareTo(o.getGender());
        else if (age.compareTo(o.getAge()) != 0)
            return age.compareTo(o.getAge());
        else if (maritalStatus.compareTo(o.getMaritalStatus()) != 0)
            return maritalStatus.compareTo(o.getMaritalStatus());
        else if (ageAtFirstMarriage.compareTo(o.getAgeAtFirstMarriage()) != 0)
            return ageAtFirstMarriage.compareTo(o.getAgeAtFirstMarriage());
        else if (attendSchool.compareTo(o.getAttendSchool()) != 0)
            return attendSchool.compareTo(o.getAttendSchool());
        else if (canRead.compareTo(o.getCanRead()) != 0)
            return canRead.compareTo(o.getCanRead());
        else if (birthplace.compareTo(o.getBirthplace()) != 0)
            return birthplace.compareTo(o.getBirthplace());
        else if (fatherBirthplace.compareTo(o.getfatherBirthplace()) != 0)
            return fatherBirthplace.compareTo(o.getfatherBirthplace());
        else if (motherBirthplace.compareTo(o.getmotherBirthplace()) != 0)
            return motherBirthplace.compareTo(o.getmotherBirthplace());
        else if (motherTongue.compareTo(o.getMotherTongue()) != 0)
            return motherTongue.compareTo(o.getMotherTongue());
        else if (yearImmigrated.compareTo(o.getYearImmigrated()) != 0)
            return yearImmigrated.compareTo(o.getYearImmigrated());
        else if (occupation.compareTo(o.getOccupation()) != 0)
            return occupation.compareTo(o.getOccupation());
        else if (industry.compareTo(o.getIndustry()) != 0)
            return industry.compareTo(o.getIndustry());
        else if (remarks.compareTo(o.getRemarks()) != 0)
            return remarks.compareTo(o.getRemarks());
        else
            return 0;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getRenting() {
        return renting;
    }

    public void setRenting(String renting) {
        this.renting = renting;
    }

    public double getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(double propertyValue) {
        this.propertyValue = propertyValue;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public double getAge() {
        return age;
    }

    public void setAge(double age) {
        this.age = age;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public int getAgeAtFirstMarriage() {
        return ageAtFirstMarriage;
    }

    public void setAgeAtFirstMarriage(int ageAtFirstMarriage) {
        this.ageAtFirstMarriage = ageAtFirstMarriage;
    }

    public String getAttendSchool() {
        return attendSchool;
    }

    public void setAttendSchool(String attendSchool) {
        this.attendSchool = attendSchool;
    }

    public String getCanRead() {
        return canRead;
    }

    public void setCanRead(String canRead) {
        this.canRead = canRead;
    }

    public String getBirthplace() {
        return birthplace;
    }

    public void setBirthplace(String birthplace) {
        this.birthplace = birthplace;
    }

    public String getfatherBirthplace() {
        return fatherBirthplace;
    }

    public void setfatherBirthplace(String fatherBirthplace) {
        this.fatherBirthplace = fatherBirthplace;
    }

    public String getmotherBirthplace() {
        return motherBirthplace;
    }

    public void setmotherBirthplace(String motherBirthplace) {
        this.motherBirthplace = motherBirthplace;
    }

    public String getMotherTongue() {
        return motherTongue;
    }

    public void setMotherTongue(String motherTongue) {
        this.motherTongue = motherTongue;
    }

    public int getYearImmigrated() {
        return yearImmigrated;
    }

    public void setYearImmigrated(int yearImmigrated) {
        this.yearImmigrated = yearImmigrated;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
