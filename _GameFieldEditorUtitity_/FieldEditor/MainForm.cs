using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.IO;

namespace FieldEditor
{
    public partial class MainForm : Form
    {
        public MainForm()
        {
            InitializeComponent();
        }
        bool[,] field = new bool[16, 16];
        string currentFile;
        string[] commentLines = new string[8];
        int commentLinesNum = 0;
        public MainForm(string file)
        {
            InitializeComponent();
            currentFile = file;
            StreamReader sr = new StreamReader(file);
            string line;
            while ((line = sr.ReadLine()).Substring(0, 1) == "#")
            {
            }
            for (int i = 0; i < 16; i++)
            {
                char[] cl = line.ToCharArray();
                for (int j = 0; j < 16; j++)
                {
                    field[i, j] = (cl[j] == '*');
                    Button btn = new Button();
                    btn.Location = new Point(i * 30, j * 30);
                    btn.Size = new Size(30, 30);
                    btn.Text = (field[i, j]?"*":"");
                    btn.Click += new EventHandler(btn_Click);
                    btn.BackColor = (field[i, j] ? Color.Green : Color.Yellow);
                    this.Controls.Add(btn);
                }
                line = sr.ReadLine();
            }
            while (line != null && line.Substring(0, 1) == "#")
            {
                commentLines[commentLinesNum] = line;
                commentLinesNum++;
                line = sr.ReadLine();
            }
            sr.Close();
        }

        void btn_Click(object sender, EventArgs e)
        {
            ((Button)sender).Text = ("*"==((Button)sender).Text?"":"*");
            int i = ((Button)sender).Location.X / 30;
            int j = ((Button)sender).Location.Y / 30;
            field[i, j] = !field[i, j];
            ((Button)sender).BackColor = (field[i, j] ? Color.Green : Color.Yellow);
        }

        private void MainForm_DragDrop(object sender, DragEventArgs e)
        {
            if (e.AllowedEffect == DragDropEffects.Move)
            {
            }
        }

        private void Save(object sender, EventArgs e)
        {
            int c = 0;
            string[] lines = new string[16];
            for (int i = 0; i < 16; i++)
            {
                lines[i] = "";
                for (int j = 0; j < 16; j++)
                {
                    if (field[i, j])
                    {
                        c++;
                        lines[i] += "*";
                    }
                    else
                    {
                        lines[i] += " ";
                    }
                }
            }
            if (c % 2 != 0)
            {
                MessageBox.Show("Неверная расстановка! Количество заполненых клеток должно быть чётным.");
                return;
            }
            else
            {
                StreamWriter sw = new StreamWriter(currentFile);
                for (int i = 0; i < 16; i++)
                {
                    sw.WriteLine(lines[i]);
                }
                for (int i = 0; i < commentLinesNum; i++)
                {
                    sw.WriteLine(commentLines[i]);
                }
                sw.Close();
            }
        }
    }
}
