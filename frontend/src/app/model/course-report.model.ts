import {CourseStatus} from "../utils/course-status";
import {LicenseCategory} from "../utils/license-category";
import {ProcessingStatus} from "../utils/processing-status";

/**
 * Model raportu kursu
 */
export class CourseReport {

  category: LicenseCategory;
  requiredAge: number;
  price: number;
  theoryHours: number;
  practiceHours: number;
  courseStatus: CourseStatus;
  passedCoursePercent: number;
  passedTheoryHours: number;
  passedPracticeHours: number;
  paymentStatus: ProcessingStatus;
  extraDrivingLessonsHours: number;
  startDate: Date;
  endTime: Date;
  comment: string;

  constructor(category: LicenseCategory, requiredAge: number, price: number,
              theoryHours: number, practiceHours: number, courseStatus: CourseStatus,
              passedCoursePercent: number, passedTheoryHours: number, passedPracticeHours: number,
              paymentStatus: ProcessingStatus, extraDrivingLessonsHours: number,
              startDate: Date, endTime: Date, comment: string) {
    this.category = category;
    this.requiredAge = requiredAge;
    this.price = price;
    this.theoryHours = theoryHours;
    this.practiceHours = practiceHours;
    this.courseStatus = courseStatus;
    this.passedCoursePercent = passedCoursePercent;
    this.passedTheoryHours = passedTheoryHours;
    this.passedPracticeHours = passedPracticeHours;
    this.paymentStatus = paymentStatus;
    this.extraDrivingLessonsHours = extraDrivingLessonsHours;
    this.startDate = startDate;
    this.endTime = endTime;
    this.comment = comment;
  }

}
