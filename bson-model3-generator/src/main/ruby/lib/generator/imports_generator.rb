class ImportsGenerator

  attr_reader :config, :model_conf, :imports

  def initialize(config, model_conf)
    @config = config
    @model_conf = model_conf
    @imports = []
  end

  def generate
    if @imports.empty?
      @imports = init_imports
    end
    code = ''
    @imports.each do |import|
      if import.empty?
        code << "\n"
      else
        code << "import #{import};\n"
      end
    end
    code
  end

  private
  def init_imports
    imports_javax = []
    imports_java = ['java.util.*']
    imports_other = ['com.alibaba.fastjson2.annotation.JSONType',
                     'com.github.fmjsjx.bson.model3.core.*',
                     'com.github.fmjsjx.bson.model3.core.util.*',
                     'org.bson.*',
                     'org.bson.conversions.Bson',
                     'org.jspecify.annotations.*']
    consts = @model_conf.consts
    fields = @model_conf.fields
    unless fields.filter { |field| field.store_field? }.empty?
      imports_other << 'com.mongodb.client.model.Updates'
    end
    if consts.any? { |const| const.type == 'datetime' }
      imports_java << 'java.time.LocalDateTime'
    end
    if consts.any? { |const| const.type == 'date' }
      imports_java << 'java.time.LocalDate'
    end
    if consts.any? { |const| const.type == 'time' }
      imports_java << 'java.time.LocalTime'
    end
    if fields.any? { |field| field.type == 'datetime' or (field.type == 'std-list' and field.value == 'datetime') }
      imports_other << 'com.github.fmjsjx.libcommon.util.DateTimeUtil'
      imports_java << 'java.time.LocalDateTime'
    end
    if fields.any? { |field| field.type == 'date' or (field.type == 'std-list' and field.value == 'date') }
      imports_other << 'com.github.fmjsjx.libcommon.util.DateTimeUtil'
      imports_java << 'java.time.LocalDate'
    end
    if fields.any? { |field| field.type == 'time' or (field.type == 'std-list' and field.value == 'time') }
      imports_other << 'com.github.fmjsjx.libcommon.util.DateTimeUtil'
      imports_java << 'java.time.LocalTime'
    end
    if fields.any? { |field| field.type == 'decimal' or (field.type == 'std-list' and field.value == 'decimal') or (field.type == 'map' and field.value == 'decimal') }
      imports_java << 'java.math.BigDecimal'
    end
    if fields.any? { |field| field.type == 'object-id' }
      imports_other << 'org.bson.types.ObjectId'
    end
    fields.filter { |field| field.virtual? }.each do |field|
      field.imports.each do |import|
        if import.start_with?('javax.')
          imports_javax << import
        elsif import.start_with?('java.')
          unless /^java\.util\.[^\.]+$/.match?(import)
            imports_java << import
          end
        else
          patterns = [/^com\.github\.fmjsjx\.bson\.model3\.core\.[^\.]+$/,
                      /^com\.github\.fmjsjx\.bson\.model3\.core\.util\.[^\.]+$/,
                      /^org\.bson\.[^\.]+$/]
          if patterns.none? { |pattern| pattern.match?(import) }
            imports_other << import
          end
        end
      end
    end
    imports = imports_other.uniq.sort
    imports << ''
    imports += imports_javax.uniq.sort
    imports += imports_java.uniq.sort
  end

end