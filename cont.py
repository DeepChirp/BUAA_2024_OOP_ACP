import os

def count_lines_in_file(file_path):
    """ 计算单个文件的行数 """
    with open(file_path, 'r', encoding='utf-8', errors='ignore') as file:
        return sum(1 for line in file)

def count_lines_in_directory(directory, indent="", is_last=False, exclude_files=None):
    """ 递归地统计指定目录下所有文件的总行数，并使用树结构输出每个文件的行数 """
    total_lines = 0

    # 输出当前目录
    if indent:
        print(indent + ("└── " if is_last else "├── ") + os.path.basename(directory) + "/")
    else:
        print(os.path.basename(directory) + "/")

    # 获取目录下的所有条目并排序
    entries = sorted([os.path.join(directory, entry) for entry in os.listdir(directory)])

    # 排除特定文件
    if exclude_files:
        entries = [entry for entry in entries if entry not in exclude_files]

    last_entry = entries[-1] if entries else None

    for i, entry in enumerate(entries):
        is_last_entry = entry == last_entry
        new_indent = indent + ("    " if is_last else "│   ")
        if os.path.isdir(entry):
            total_lines += count_lines_in_directory(entry, new_indent, is_last_entry, exclude_files)
        elif os.path.isfile(entry):
            try:
                lines = count_lines_in_file(entry)
                total_lines += lines
                prefix = "└── " if is_last_entry else "├── "
                print(new_indent + prefix + os.path.basename(entry) + ": " + str(lines) + " 行")
            except Exception as e:
                print(f"Error reading {entry}: {e}")

    return total_lines

if __name__ == "__main__":
    # 获取当前工作目录
    directory = os.getcwd()
    # 获取当前脚本的完整路径
    script_path = os.path.abspath(__file__)
    # 创建排除文件列表
    exclude_files = [script_path]

    # 只统计 .\src 和 .\test 目录
    total_lines = 0
    for subdir in ['src', 'test']:
        subdir_path = os.path.join(directory, subdir)
        if os.path.exists(subdir_path):
            print("    ", end="")
            total_lines += count_lines_in_directory(subdir_path, is_last=True, exclude_files=exclude_files)

    print(f"总行数: {total_lines}")

    input("按任意键退出...")