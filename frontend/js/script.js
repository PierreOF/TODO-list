let tasks = [];
let editIndex = null;

function addTask() {
    const title = document.getElementById("task-title").value;
    const desc = document.getElementById("task-desc").value;
    const dueDate = document.getElementById("task-due-date").value;
    const priority = document.getElementById("task-priority").value;
    const category = document.getElementById("task-category").value;
    const status = document.getElementById("task-status").value;

    if (title.trim() === "") {
        alert("Task title is required!");
        return;
    }

    if (editIndex !== null) {
        tasks[editIndex] = { title, desc, dueDate, priority, category, status };
        editIndex = null;
        document.getElementById("save-task-btn").textContent = "Save Task";
    } else {
        tasks.push({ title, desc, dueDate, priority, category, status });
    }

    renderTasks();
    clearForm();
}

function renderTasks(filter = "ALL") {
    const taskList = document.getElementById("task-list");
    const sortOption = document.getElementById("sort-tasks").value;
    taskList.innerHTML = "";

    let filteredTasks = tasks
        .map((task, index) => ({ ...task, originalIndex: index }))
        .filter(task => filter === "ALL" || task.status === filter);

    // Ordenação conforme a escolha do usuário
    if (sortOption === "priority") {
        const priorityOrder = ["LOW", "NORMAL", "IMPORTANT", "VERY IMPORTANT", "CRITICAL"];
        filteredTasks.sort((a, b) => priorityOrder.indexOf(b.priority) - priorityOrder.indexOf(a.priority));
    } else if (sortOption === "status") {
        const statusOrder = ["TODO", "DOING", "DONE"];
        filteredTasks.sort((a, b) => statusOrder.indexOf(a.status) - statusOrder.indexOf(b.status));
    }

    filteredTasks.forEach(task => {
        const li = document.createElement("li");
        li.innerHTML = `<strong>${task.title}</strong> [ ${task.priority} ] [ ${task.status} ]
                        <span class='actions'>
                            <button onclick='openModal(${task.originalIndex})'>View</button>
                            <button onclick='editTask(${task.originalIndex})'>Edit</button>
                            <button onclick='deleteTask(${task.originalIndex})'>Delete</button>
                        </span>`;
        taskList.appendChild(li);
    });
}

function openModal(index) {
    const task = tasks[index];
    document.getElementById("modal-task-title").textContent = task.title;
    document.getElementById("modal-task-desc").textContent = task.desc;
    document.getElementById("modal-task-due-date").textContent = task.dueDate;
    document.getElementById("modal-task-priority").textContent = task.priority;
    document.getElementById("modal-task-category").textContent = task.category;
    document.getElementById("modal-task-status").textContent = task.status;

    document.getElementById("task-modal").style.display = "flex";
}

function closeModal() {
    document.getElementById("task-modal").style.display = "none";
}

window.onclick = function(event) {
    const modal = document.getElementById("task-modal");
    if (event.target === modal) {
        modal.style.display = "none";
    }
};

function clearForm() {
    document.getElementById("task-title").value = "";
    document.getElementById("task-desc").value = "";
    document.getElementById("task-due-date").value = "";
    document.getElementById("task-priority").value = "LOW";
    document.getElementById("task-category").value = "";
    document.getElementById("task-status").value = "TODO";
}

function deleteTask(index) {
    tasks.splice(index, 1);
    renderTasks();
}

function editTask(index) {
    const task = tasks[index];
    document.getElementById("task-title").value = task.title;
    document.getElementById("task-desc").value = task.desc;
    document.getElementById("task-due-date").value = task.dueDate;
    document.getElementById("task-priority").value = task.priority;
    document.getElementById("task-category").value = task.category;
    document.getElementById("task-status").value = task.status;

    editIndex = index;
    document.getElementById("save-task-btn").textContent = "Update Task";
}

function filterTasks(status) {
    renderTasks(status);
}

document.addEventListener("DOMContentLoaded", () => renderTasks());
