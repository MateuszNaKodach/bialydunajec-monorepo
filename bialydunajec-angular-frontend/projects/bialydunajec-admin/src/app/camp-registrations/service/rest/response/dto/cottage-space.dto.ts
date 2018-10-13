export class CottageSpaceDto {
  fullCapacity: number;
  reservations: number;
  maxFemaleTotal?: number;
  maxMaleTotal?: number;
  highSchoolRecentGraduatesCapacity?: number;
  maxFemaleHighSchoolRecentGraduates?: number;
  maxMaleHighSchoolRecentGraduates?: number;

  constructor(fullCapacity: number = null, reservations: number = null, maxFemaleTotal: number = null, maxMaleTotal: number = null, highSchoolRecentGraduatesCapacity: number = null, maxFemaleHighSchoolRecentGraduates: number = null, maxMaleHighSchoolRecentGraduates: number = null) {
    this.fullCapacity = fullCapacity;
    this.reservations = reservations;
    this.maxFemaleTotal = maxFemaleTotal;
    this.maxMaleTotal = maxMaleTotal;
    this.highSchoolRecentGraduatesCapacity = highSchoolRecentGraduatesCapacity;
    this.maxFemaleHighSchoolRecentGraduates = maxFemaleHighSchoolRecentGraduates;
    this.maxMaleHighSchoolRecentGraduates = maxMaleHighSchoolRecentGraduates;
  }
}
