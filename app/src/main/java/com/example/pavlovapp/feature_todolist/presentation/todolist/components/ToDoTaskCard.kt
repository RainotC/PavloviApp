package com.example.pavlovapp.feature_todolist.presentation.todolist.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import com.example.pavlovapp.feature_todolist.domain.model.ToDoTask
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pavlovapp.core.presentation.CompleteButton
import androidx.compose.material3.Text
import androidx.compose.ui.text.style.TextOverflow
import com.example.pavlovapp.core.presentation.DeleteButton


@Composable
fun ToDoTaskCard(
    todo: ToDoTask,
    modifier: Modifier = Modifier,
    onDeleteClick: () -> Unit,
    onCompleteClick: () -> Unit,
    onCardClick: () -> Unit,
){

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        onClick = onCardClick,
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surfaceContainerLow)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CompleteButton(
                onCompleteClick, todo.completed
            )
            Text(
                text = todo.title,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f),
                overflow = TextOverflow.Ellipsis
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Deadline: ${todo.deadline}",
                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.weight(1f).padding(8.dp),
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.weight(1.3f))
            DeleteButton(onDeleteClick)
        }
    }


}


@Preview
@Composable
fun ToDoTaskCardPreview() {
    ToDoTaskCard(
        todo = ToDoTask(
            id = 1,
            title = "Sample Task",
            timestamp = 123456,
            deadline = 12042025,
            completed = false
        ),
        onDeleteClick = {},
        onCompleteClick = {},
        onCardClick = {}
    )
}