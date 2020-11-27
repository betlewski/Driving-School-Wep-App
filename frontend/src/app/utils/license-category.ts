/**
 * Kategoria prawa jazdy
 */
export class LicenseCategory {

  public static readonly AM = new LicenseCategory("AM", 14, 1000, 10);
  public static readonly A1 = new LicenseCategory("A1", 16, 1300, 30);
  public static readonly A2 = new LicenseCategory("A2", 18, 1400, 30);
  public static readonly A = new LicenseCategory("A", 24, 1500, 30);
  public static readonly B = new LicenseCategory("B", 18, 1800, 30);

  public name: string;
  public requiredAge: number;
  public price: number;
  public practiceHours: number;

  private constructor(name: string, requiredAge: number, price: number, practiceHours: number) {
    this.name = name;
    this.requiredAge = requiredAge;
    this.price = price;
    this.practiceHours = practiceHours;
  }

  public static values(): LicenseCategory[] {
    return [LicenseCategory.AM, LicenseCategory.A1, LicenseCategory.A2, LicenseCategory.A, LicenseCategory.B];
  }

}
