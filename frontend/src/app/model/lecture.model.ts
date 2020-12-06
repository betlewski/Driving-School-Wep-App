/**
 * Pojedynczy wykład prowadzony w ramach cyklu wykładów.
 */
export class Lecture {

  id: number;
  subject: string;
  startTime: Date;
  endTime: Date;
  additionalInfo: string;

  constructor(id: number, subject: string, startTime: Date,
              endTime: Date, additionalInfo: string) {
    this.id = id;
    this.subject = subject;
    this.startTime = startTime;
    this.endTime = endTime;
    this.additionalInfo = additionalInfo;
  }

}
