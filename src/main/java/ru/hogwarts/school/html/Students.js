
    function fetchAndDisplayStudents() {
    fetch('http://localhost:8080/student/all') // Отправляем GET-запрос на /student/all
        .then(response => response.json()) // Преобразуем ответ в JSON
        .then(data => {
            // Вызываем функцию для отображения данных
            displayStudents(data);
            closeActiveModal(); // Close the active modal after displaying the data
        })
        .catch(error => console.error('Ошибка при получении данных:', error));
}

    function createStudent() {
    const studentName = document.getElementById('studentName').value;
    const studentAge = parseInt(document.getElementById('studentAge').value); // Parse the age as an integer
    if (studentName.trim() === '' || isNaN(studentAge) || studentAge<1) {
    alert('Введите корректные данные для создания студента');
    return;
}
    const newStudent = {
    name: studentName,
    age: studentAge

};

    fetch('http://localhost:8080/student', {
    method: 'POST',
    headers: {
    'Content-Type': 'application/json; charset=utf-8',
},
    body: JSON.stringify(newStudent),
})
    .then(response => response.json())
    .then(data => {
    // If you want to do something with the response data
    console.log('Created student:', data);
    // You can also fetch and display the updated list of students here if needed
    fetchAndDisplayStudents();
})
    .catch(error => console.error('Ошибка при создании студента:', error));
}

    function searchStudentsByName() {
    const searchName = document.getElementById('searchName').value;

    fetch(`http://localhost:8080/student/all?name=${encodeURIComponent(searchName)}`)
    .then(response => response.json())
    .then(data => {
    displayStudents(data);
        closeActiveModal(); // Close the active modal after displaying the data
})
    .catch(error => console.error('Ошибка при поиске студентов:', error));
}

    function displayStudents(students) {
    const table = document.querySelector('.studentsTable');
        const tableContainer = document.createElement('div'); // Create a container for the table
        tableContainer.classList.add('scrollable-list');

            table.innerHTML = `
            <tr>
                <th>ID</th>
                <th>Имя</th>
                <th>Возраст</th>
                <th>Факультет</th>
            </tr>

        `;
        students.forEach(student => {
            const row = table.insertRow();
            row.innerHTML = `
            <td>${student.id}</td>
            <td>${student.name}</td>
            <td>${student.age}</td>
            <td>${student.faculty ? student.faculty.name : 'Нет данных'}</td>
        `;
        });
        // Clear the previous content and append the table to the container
        tableContainer.innerHTML = '';
        tableContainer.appendChild(table);

        // Append the container to the main content area
        const contentArea = document.querySelector('.main-content');
        contentArea.innerHTML = '';
        contentArea.appendChild(tableContainer);
}
    let activeModal = null; // Store the reference to the active modal


    function showStudentListQuery() {
    closeActiveModal()
        const studentListModal = document.getElementById('studentListModal');
        studentListModal.classList.add('active');
        activeModal = studentListModal;
    }

    function showSearchStudentQuery() {
    closeActiveModal()
        const searchStudentModal = document.getElementById('searchStudentModal');
        searchStudentModal.classList.add('active');
        activeModal = searchStudentModal;
    }

    function showCreateStudentQuery() {
    closeActiveModal()
        const createStudentModal = document.getElementById('createStudentModal');
        createStudentModal.classList.add('active');
        activeModal = createStudentModal;
    }
    function closeActiveModal() {
        if (activeModal) {
            activeModal.classList.remove('active');
        }
    }
