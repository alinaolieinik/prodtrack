export interface Announcement {
    id: number;
    title: string;
    message: string;
    createdAt: string;
    createdBy: string;
    validFrom: string;
    validTo: string | null;
}

export interface OperatorDashboard {
    operatorId: number;
    name: string;
    surname: string;
    machineName: string;
    productName: string;
    packedCount: number;
    targetPerShift: number;
    exceedTargetPerShift: number;
    minimumPerShift: number;
    secondsPerPackage: number;
    remainingSeconds: number;
    announcements: Announcement[];
}
