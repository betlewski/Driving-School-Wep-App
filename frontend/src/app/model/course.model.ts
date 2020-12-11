import {CourseStatus} from "../utils/course-status";
import {LicenseCategory} from "../utils/license-category";

/**
 * Model danych kursu
 */
export class Course {

  licenseCategory: LicenseCategory;
  courseStatus: CourseStatus;
  startDate: Date;

  constructor(licenseCategory: LicenseCategory, courseStatus: CourseStatus, startDate: Date) {
    this.licenseCategory = licenseCategory;
    this.courseStatus = courseStatus;
    this.startDate = startDate;
  }

}
