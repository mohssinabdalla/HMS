package service;

/**
 * ONE-TIME SEEDER — run this once to generate sample data files using FileHelper.
 * This is not part of the real app flow; it exists purely to create test data
 * the same way the real app eventually will (via FileHelper.appendLine),
 * rather than the files being hand-typed.
 */
public class DataSeeder {

    public static void main(String[] args) {
        // Admin — 7 columns (includes department)
        FileHelper.appendLine("data/admins.txt", "A001,admin1,admin123,Raja Kumar,admin1@apu.edu,0123456789,IT");
        FileHelper.appendLine("data/admins.txt", "A002,admin2,admin456,Nurul Huda,admin2@apu.edu,0123456790,Operations");

        // Doctor — 6 columns for now (stub stage, no extra fields yet)
        FileHelper.appendLine("data/doctors.txt", "D001,drchen,doctor123,Dr Chen,chen@apu.edu,0123456780");
        FileHelper.appendLine("data/doctors.txt", "D002,drlim,doctor456,Dr Lim,lim@apu.edu,0123456781");

        // Patient — 6 columns
        FileHelper.appendLine("data/patients.txt", "P001,patient1,patient123,Ali Hassan,ali@apu.edu,0123456782");
        FileHelper.appendLine("data/patients.txt", "P002,patient2,patient456,Mei Ling,mei@apu.edu,0123456783");

        // MedicalManager — 6 columns
        FileHelper.appendLine("data/medicalManagers.txt", "M001,manager1,manager123,Siti Aisha,siti@apu.edu,0123456784");

        System.out.println("Seed data written using FileHelper.appendLine().");
    }
}
