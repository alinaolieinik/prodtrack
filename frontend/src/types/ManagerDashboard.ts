export interface ManagerDashboard {
    manager: {
        id: number;
        name: string;
        surname: string;
    };
    summary: {
        operatorsCount: number;
        machinesCount: number;
        workingMachinesCount: number;
        waitingMachinesCount: number;
        failedMachinesCount: number;
        activeAnnouncementsCount: number;
    };
    users: ManagerUser[];
    machines: ManagerMachine[];
    announcements: Announcement[];
}

export interface ManagerUser {
    id: number;
    name: string;
    surname: string;
    role: "OPERATOR" | "MANAGER";
    machineId: number | null;
    machineName: string | null;
    productId: number | null;
    productName: string | null;
    productCode: string | null;
}

export interface ManagerMachine {
    id: number;
    name: string;
    state: "WORKING" | "WAITING" | "FAILURE";
    lastUpdate: string;
}

export interface ProductionNorm {
    id: number;
    targetPerShift: number;
    exceedTargetPerShift: number;
    minimumPerShift: number;
    secondsPerPackage: number;
    validFrom: string;
}

export interface Announcement {
    id: number;
    title: string;
    message: string;
    createdBy: string;
    createdAt: string;
    validFrom: string;
    validTo: string | null;
}