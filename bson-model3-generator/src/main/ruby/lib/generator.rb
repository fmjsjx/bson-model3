require_relative 'generator/model_generator'
require 'fileutils'

class Generator

  attr_reader :config, :out_dir

  def initialize(config, out_dir)
    @config = config
    @out_dir = out_dir
  end

  def generate
    package_dir = generate_package_dir
    generated_contents = @config.models.map do |model_conf|
      puts "Generating model: #{model_conf.package}.#{model_conf.name}..."
      content = ModelGenerator.new(@config, model_conf).generate
      puts "OK"
      [model_conf, content]
    end
    generated_contents.each do |model_conf, content|
      file_path = File.join(package_dir, "#{model_conf.name}.java")
      puts "Saving model: #{model_conf.package}.#{model_conf.name} => #{file_path}..."
      File.write(file_path, content)
      puts "OK"
    end
  end

  private
  def generate_package_dir
    package_dir = File.join(@out_dir, File.join(config.package.split('.')))
    unless File.directory?(package_dir)
      puts "Creating package directory: #{@config.package} => #{package_dir}..."
      FileUtils.mkdir_p(package_dir)
      puts "OK"
    end
    package_dir
  end

end
