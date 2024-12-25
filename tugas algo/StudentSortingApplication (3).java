import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class StudentSortingApplication extends JFrame {
    private JTextField nameField;
    private JTextField inputField;
    private JTextArea resultArea;
    private JLabel totalLabel;
    private JLabel averageLabel;

    public StudentSortingApplication() {
        setTitle("Student Sorting Application");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel dengan gradasi
        GradientPanel mainPanel = new GradientPanel();
        mainPanel.setLayout(new GridBagLayout());
        add(mainPanel);

        // Panel tengah dengan background putih
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setLayout(new GridBagLayout());
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        centerPanel.setOpaque(true);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Tambahkan Judul
        JLabel titleLabel = new JLabel("Nilai Mahasiswa");
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Agar label berada di tengah
        centerPanel.add(titleLabel, gbc);

        // Reset gridwidth untuk elemen berikutnya
        gbc.gridwidth = 1;
        gbc.gridy++;

        // Nama Mahasiswa
        JLabel nameLabel = new JLabel("Nama Mahasiswa:");
        nameLabel.setForeground(Color.BLACK);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        centerPanel.add(nameLabel, gbc);

        nameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        centerPanel.add(nameField, gbc);

        // Masukkan Nilai
        JLabel inputLabel = new JLabel("Masukkan Nilai (pisahkan dengan koma):");
        inputLabel.setForeground(Color.BLACK);
        inputLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        centerPanel.add(inputLabel, gbc);

        inputField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        centerPanel.add(inputField, gbc);

        // Tombol Sort
        JButton sortButton = new JButton("Sort");
        sortButton.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 1;
        gbc.gridy = 3;
        centerPanel.add(sortButton, gbc);

        // Area Hasil
        resultArea = new JTextArea(5, 30);
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(resultArea);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        centerPanel.add(scrollPane, gbc);

        // Label Total dan Rata-rata
        JPanel statsPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        totalLabel = new JLabel("Total: -");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 14));
        statsPanel.add(totalLabel);

        averageLabel = new JLabel("Rata-rata: -");
        averageLabel.setFont(new Font("Arial", Font.BOLD, 14));
        statsPanel.add(averageLabel);

        gbc.gridy = 5;
        gbc.gridwidth = 2;
        centerPanel.add(statsPanel, gbc);

        // Tambahkan panel tengah ke panel utama
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(centerPanel, gbc);

        // Event Listener Tombol Sort
        sortButton.addActionListener(new SortButtonListener());

        setVisible(true);
    }

    // Kelas Panel dengan gradasi
    class GradientPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            int width = getWidth();
            int height = getHeight();

            // Gradasi dari biru (#361CCB) ke hijau (#5EE4B4)
            GradientPaint gradient = new GradientPaint(0, 0, Color.decode("#361CCB"),
                    0, height, Color.decode("#5EE4B4"));
            g2d.setPaint(gradient);
            g2d.fillRect(0, 0, width, height);
        }
    }

    // Logika Sorting
    private class SortButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = nameField.getText();
            String inputText = inputField.getText();

            if (name.isEmpty() || inputText.isEmpty()) {
                resultArea.setText("Harap masukkan nama dan nilai terlebih dahulu.");
                return;
            }

            try {
                String[] inputStrings = inputText.split(",");
                int[] numbers = Arrays.stream(inputStrings).mapToInt(Integer::parseInt).toArray();

                bubbleSort(numbers);

                int total = Arrays.stream(numbers).sum();
                double average = total / (double) numbers.length;

                resultArea.setText("Nama Mahasiswa: " + name + "\n" +
                        "Nilai setelah diurutkan:\n" +
                        Arrays.toString(numbers));

                totalLabel.setText("Total: " + total);
                averageLabel.setText("Rata-rata: " + String.format("%.2f", average));
            } catch (NumberFormatException ex) {
                resultArea.setText("Harap masukkan nilai berupa angka yang dipisahkan dengan koma.");
            }
        }

        private void bubbleSort(int[] arr) {
            int n = arr.length;
            for (int i = 0; i < n - 1; i++) {
                for (int j = 0; j < n - i - 1; j++) {
                    if (arr[j] > arr[j + 1]) {
                        int temp = arr[j];
                        arr[j] = arr[j + 1];
                        arr[j + 1] = temp;
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StudentSortingApplication::new);
    }
}
