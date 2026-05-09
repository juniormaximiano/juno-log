export type Task = {
    id: number;
    taskName: string;
    description: string;
    status: "PENDING" | "DONE";
    createdAt: string;
    finishedAt: string | null;
}

 