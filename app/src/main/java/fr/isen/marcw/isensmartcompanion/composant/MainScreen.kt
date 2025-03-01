package fr.isen.marcw.isensmartcompanion.composant

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import fr.isen.marcw.isensmartcompanion.AppNavigation
import fr.isen.marcw.isensmartcompanion.R
import fr.isen.marcw.isensmartcompanion.service.GeminiService
import fr.isen.marcw.isensmartcompanion.ui.theme.ISENSmartCompanionTheme
import kotlinx.coroutines.launch
import androidx.compose.material.icons.outlined.Delete

@Composable
fun MainScreen(navController: NavController? = null) {
    var question by remember { mutableStateOf("") }
    var messages by remember { mutableStateOf<List<Pair<String, Boolean>>>(emptyList()) }
    var conversationContext by remember { mutableStateOf("") }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier.fillMaxSize().background(Color(0xFFFAF8FC)).padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.isen_logo),
            contentDescription = "Logo ISEN",
            modifier = Modifier.size(100.dp).clip(CircleShape)
        )
        Text(text = "Smart Companion", fontSize = 18.sp, color = Color.Gray)

        Spacer(modifier = Modifier.height(16.dp))

        // ✅ Liste des messages style conversation WhatsApp avec défilement
        Column(
            modifier = Modifier.fillMaxWidth().weight(1f).verticalScroll(scrollState)
        ) {
            messages.forEach { (message, isUser) ->
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = if (isUser) Alignment.CenterEnd else Alignment.CenterStart
                ) {
                    Card(
                        modifier = Modifier.padding(8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isUser) Color(0xFFFFCDD2) else Color(0xFFE0E0E0)
                        )
                    ) {
                        Text(
                            text = message,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(16.dp),
                            color = Color.Black
                        )
                    }
                }
            }
        }

        // ✅ Barre de saisie + bouton clear
        Row(
            modifier = Modifier.fillMaxWidth().background(Color(0xFFE0E0E0), shape = RoundedCornerShape(50)).padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicTextField(
                value = question,
                onValueChange = { question = it },
                textStyle = TextStyle(fontSize = 16.sp),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Send),
                keyboardActions = KeyboardActions(
                    onSend = {
                        if (question.isNotBlank()) {
                            coroutineScope.launch {
                                messages = messages + (question to true)
                                val contextToUse = if (conversationContext.isNotBlank()) "$conversationContext\n$question" else question
                                val response = GeminiService.getResponse(contextToUse)
                                messages = messages + (response to false)
                                conversationContext += "\n$question\n$response"
                                question = ""
                            }
                        }
                    }
                ),
                modifier = Modifier.weight(1f).padding(end = 8.dp)
            ) { innerTextField ->
                Box(modifier = Modifier.fillMaxWidth()) {
                    if (question.isEmpty()) {
                        Text(text = "Posez-moi une question...", fontSize = 16.sp, color = Color.Gray)
                    }
                    innerTextField()
                }
            }

            IconButton(onClick = {
                if (question.isNotBlank()) {
                    coroutineScope.launch {
                        messages = messages + (question to true)
                        val contextToUse = if (conversationContext.isNotBlank()) "$conversationContext\n$question" else question
                        val response = GeminiService.getResponse(contextToUse)
                        messages = messages + (response to false)
                        conversationContext += "\n$question\n$response"
                        question = ""
                    }
                }
            }) {
                Icon(imageVector = Icons.Outlined.Send, contentDescription = "Envoyer", tint = Color.Red, modifier = Modifier.size(24.dp))
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // ✅ Bouton pour clear la conversation
        Button(onClick = {
            messages = emptyList()
            conversationContext = ""
        }, colors = ButtonDefaults.buttonColors(containerColor = Color.Red)) {
            Icon(imageVector = Icons.Outlined.Delete, contentDescription = "Effacer", tint = Color.White)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Effacer la conversation", color = Color.White)
        }
    }
}



@Preview(showBackground = true)
@Composable
fun PreviewAppNavigation() {
    ISENSmartCompanionTheme {
        AppNavigation() // Aperçu complet de la navigation
    }
}
